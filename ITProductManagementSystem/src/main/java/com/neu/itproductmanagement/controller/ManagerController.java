package com.neu.itproductmanagement.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.itproductmanagement.dao.ProductDAO;
import com.neu.itproductmanagement.dao.ProductRequestDAO;
import com.neu.itproductmanagement.pojo.Product;
import com.neu.itproductmanagement.pojo.ProductRequest;
import com.neu.itproductmanagement.pojo.Student;

@Controller
public class ManagerController {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	/*
	 * MANAGER ---> MANAGE REQUEST AND VIEW ALL REQUESTS
	 * 
	 */
	@Autowired
	ProductRequestDAO productRequestDAO;

	@Autowired
	ProductDAO productDAO;

	// return manage request page
	@RequestMapping(value = "/login/managerhome/managerequest")
	public String viewRequests(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
		HttpSession session = request.getSession();
		logger.info("IN GET Manage request" + session.getAttribute("role"));
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return "loginrequired";
		} else if (role.equals("Student")) {
			return "unauthorized";
		} else {
		
		List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
		model.addAttribute("productrequestList", productrequestList);
		return "managerequest";
		}
	}

	// return view all  request page
		@RequestMapping(value = "/login/managerhome/viewallrequests")
		public String viewAllRequests(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			HttpSession session = request.getSession();
			logger.info("IN view all requests" + session.getAttribute("role"));
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Student")) {
				return "unauthorized";
			} else {
				
			List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
			model.addAttribute("productrequestList", productrequestList);
			return "viewallrequests";
			}
		}
	// method that accepts or rejects request
	@RequestMapping(value = "/login/managerhome/processRequest", method = RequestMethod.POST)
	public String proccessRequest(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
		
		HttpSession session = request.getSession();
		logger.info("IN POST MANAGE " + session.getAttribute("role"));
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return "loginrequired";
		} else if (role.equals("Student")) {
			return "unauthorized";
		} else {
		
		String result = request.getParameter("decision");
		System.out.println("Value chosen is :" + result);

		long requestId = Long.parseLong(request.getParameter("id"));
		ProductRequest pr = productRequestDAO.getRequestById(requestId);
		System.out.println("$$$$$$$$$$$$$$$$$Selected pr" + pr);
		if (pr == null) {
			List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
			request.setAttribute("error", "Request does not exist");
			model.addAttribute("productrequestList", productrequestList);
			return "managerequest";
		}
		String comments = request.getParameter("comments");
		System.out.println("*************************************************" + pr.getId());
		System.out.println("*************************************************" + pr.getStatus());

		String requeststatus = pr.getStatus();
		if (requeststatus.equalsIgnoreCase("pending")) {
			if (result.equalsIgnoreCase("accept")) {
				int totalquantity = pr.getProduct().getQuantity();
				int rentedquantity = pr.getProduct().getRentedquantity();
				System.out.println("***********************************Inside approved if loop");
				if (rentedquantity < totalquantity) {
					Date currentDate = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);
					c.add(Calendar.DATE, 1);
					Date newDate = c.getTime();
					pr.setApprovaldate(currentDate);
					pr.setStatus("approved");
					pr.setExpectedReturnDate(newDate);
					pr.setComments(comments);
					productRequestDAO.updaterequest(pr);

					// if product request executes then product table should be updated
					productDAO.updateRentedProductCount(pr.getProduct().getId());
					return "managerhome";
				}
				List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
				request.setAttribute("error", "Product not available");
				model.addAttribute("productrequestList", productrequestList);
				return "managerequest";

			}

			if (result.equalsIgnoreCase("reject")) {
				System.out.println("***********************************Inside reject if loop");
				Date currentDate = new Date();
				String comment = request.getParameter("comments");
				pr.setApprovaldate(currentDate);
				pr.setStatus("rejected");
				pr.setComments(comment);
				productRequestDAO.updaterequest(pr);

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
