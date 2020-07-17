package com.neu.itproductmanagement.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.itproductmanagement.dao.ProductDAO;
import com.neu.itproductmanagement.dao.ProductRequestDAO;
import com.neu.itproductmanagement.pojo.ProductRequest;

@Controller
public class ReturnController {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static final Logger logger = LoggerFactory.getLogger(ReturnController.class);
	
	@Autowired
	ProductRequestDAO productRequestDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	// InitBinder is a preprocessor and is used here to remove white spaces
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
			webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		}
		
		// Method to send email
	    public void sendEmail(String emailID, String productname) throws EmailException {
	        try {
	            Email email = new SimpleEmail();
	            email.setHostName("smtp.gmail.com");
	            email.setSmtpPort(465);
	            email.setAuthenticator(new DefaultAuthenticator("webtoolsproject2020@gmail.com", "1q_2w_3e_4r"));
	            email.setSSLOnConnect(true);
	            email.setFrom("no-reply@msis.neu.edu");
	            email.setSubject("Lost Product");
	            email.setMsg("You have been fines $200 for losing product -"+productname);
	            email.addTo(emailID);
	            email.send();
	        } catch (Exception ex) {
	            logger.info("Unable to send email");
	            System.out.println(ex);
	        }
	    }
		
	//method to redirect student to requests which needs attention
		@RequestMapping(value = "/login/studenthome/returnproduct")
		public String viewActionRequiredRequests(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Manager")) {
				return "unauthorized";
			} else {
			
			List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
			model.addAttribute("productrequestList", productrequestList);
			return "studentreturnproduct";
			}
		}
		
	//method to process return product from student side
		@RequestMapping(value = "/login/studenthome/returnproduct" , method = RequestMethod.POST)
		public String processProductReturn(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Manager")) {
				return "unauthorized";
			} else {
			
			//take request id from user input
			long requestId = Long.parseLong(request.getParameter("id"));
			ProductRequest pr = productRequestDAO.getRequestById(requestId);
			System.out.println("$$$$$$$$$$$$$$$$$Selected pr" + pr);
			if (pr == null) {
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Request does not exist");
				model.addAttribute("productrequestList", productrequestList);
				return "studentreturnproduct";
			}
			String requestuser = (String) session.getAttribute("user");
			if(!requestuser.equalsIgnoreCase(pr.getStudent().getEmailId())) {
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Request does not belong to you");
				model.addAttribute("productrequestList", productrequestList);
				return "studentreturnproduct";
			}
			if(!pr.getStatus().equalsIgnoreCase("approved")) {
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Kindly enter active request ID to proceed");
				model.addAttribute("productrequestList", productrequestList);
				return "studentreturnproduct";
			}
			Date currentDate = new Date();
			pr.setActualReturnDate(currentDate);
			pr.setStatus("Return Initialized");
			productRequestDAO.updaterequest(pr);
			return "redirect:/studenthome";
			
			}
		}
		
		
		////MANAGER RETURN
		
		// return manage return page
		@RequestMapping(value = "/login/managerhome/managereturns")
		public String viewRequests(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Student")) {
				return "unauthorized";
			} else {
			List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
			model.addAttribute("productrequestList", productrequestList);
			return "managereturn";
			}
		}
		
		
		//Actual method that will process returns
		@RequestMapping(value = "/login/managerhome/processReturns", method = RequestMethod.POST)
		public String proccessRequest(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Student")) {
				return "unauthorized";
			} else {
			
			
			String result = request.getParameter("decision");
			String comments = request.getParameter("comments");
			System.out.println("Value chosen is :" + result);

			long requestId = Long.parseLong(request.getParameter("id"));
			ProductRequest pr = productRequestDAO.getRequestById(requestId);
			System.out.println("$$$$$$$$$$$$$$$$$Selected pr" + pr);
			
			//for invalid requests that does not exists
			if (pr == null) {
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Invalid input, Request does not exist");
				model.addAttribute("productrequestList", productrequestList);
				return "managereturn";
			}
			
			System.out.println("*************************************************" + pr.getId());
			System.out.println("*************************************************Status is " + pr.getStatus());
			String requeststatus = pr.getStatus();
			//for request that are not in correct return state
            if(!requeststatus.equalsIgnoreCase("Return Initialized")) {
            	List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Please check entered request ID, request is already processed or is not yet initialized for return");
				model.addAttribute("productrequestList", productrequestList);
				return "managereturn";
            }
			//for correct requests for returns
			if (requeststatus.equalsIgnoreCase("Return Initialized")) {
				if (result.equalsIgnoreCase("Confirm Return")) {
					long difference = 0;
					double fine = 0.0;
					Date expected;
					Date actual;
					int totalquantity = pr.getProduct().getQuantity();
					int rentedquantity = pr.getProduct().getRentedquantity();
					System.out.println("***********************************Inside approved if loop");
					if (rentedquantity > 0) {
						// Set status, process date and comments and fine 
						Date currentDate = new Date();
						pr.setConfirmReturnDate(currentDate);
						pr.setStatus("Confirm Return");
						pr.setComments(comments);
						//Date difference calculation
						expected = pr.getExpectedReturnDate();
						actual = pr.getActualReturnDate();
						
						
						//BELOW CODE IF FOR CALCULATING FINE INCREASING ACTUAL RETURN DATE BY 4 DAYS
						Calendar c1 = Calendar.getInstance();
				        c1.setTime(actual);
				        c1.add(Calendar.DATE, 4);
				        Date newactual = c1.getTime();//NEW ACTUAL DATE IS NOT SAVED IN DB UNCOMMENT BELOW TO SAVE THAT
				        System.out.println("$$$$$$$$$$$$$$$$$$$newactual date="+newactual);
				        //pr.setActualReturnDate(newactual);//UNCOMMENT while testig fine
				        difference = Math.abs((actual.getTime() - expected.getTime())/86400000);
				        //difference = Math.abs((newactual.getTime() - expected.getTime())/86400000); ///UNCOMMENT if want to check fine generation
						System.out.println("###########difference"+difference);
						//fine = difference between expected date and actual date
						//fine = 10.0 * difference;
						 if( difference > 1) {
							 fine = difference * 10.0;
						 }
						 else {
							 fine = 0.0;
						 }
						 pr.setFine(fine);
						productRequestDAO.updaterequest(pr);

						// Product return successful so now rented quantity should decrement
						productDAO.updateReturnRentedProductCount(pr.getProduct().getId()); 
						return "managerhome";
					}
					List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
					request.setAttribute("error", "Please check carefully,as per records nothing was rented");
					model.addAttribute("productrequestList", productrequestList);
					return "managerequest";

				}

				if (result.equalsIgnoreCase("Mark Lost")) {
					System.out.println("***********************************Inside reject if loop");
					Date currentDate = new Date();
					String comment = request.getParameter("comments");
					pr.setConfirmReturnDate(currentDate);;
					pr.setStatus("LOST/DAMAGED");
					pr.setComments(comment);
					pr.setFine(200.00);
					productRequestDAO.updaterequest(pr);// update request status and fine
					//update total and rented product count
					productDAO.updateTotalAndRentedProductCount(pr.getProduct().getId());
					
					try {
		                logger.info("Data successfully stored in Request Table");
		                logger.info("Sending confirmation email");
		                sendEmail(pr.getStudent().getEmailId(), pr.getProduct().getProductName());
		            } catch (EmailException e) {
		                e.printStackTrace();
		            }
					
					
					return "managerhome";
				}
			} else {
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Request already processed");
				model.addAttribute("productrequestList", productrequestList);
				return "managerequest";
			}
			return "managerhome";
		}
		}	
}
