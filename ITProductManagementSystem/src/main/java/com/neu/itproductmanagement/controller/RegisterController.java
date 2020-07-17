package com.neu.itproductmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.itproductmanagement.dao.LoginDAO;
import com.neu.itproductmanagement.dao.RegisterDAO;
import com.neu.itproductmanagement.exception.ManagerException;
import com.neu.itproductmanagement.exception.StudentException;
import com.neu.itproductmanagement.pojo.Manager;
import com.neu.itproductmanagement.pojo.Student;

@Controller
public class RegisterController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private RegisterDAO registerDao;

	@Autowired
	private LoginDAO loginDao;

	// InitBinder is a preprocessor and is used here to remove white spaces
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
			webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		}
	
	
	//FOR LOGOUT
	@RequestMapping("/home")
	public String showHome(HttpServletRequest request) {
		HttpSession session = request.getSession();
		logger.info("*********Current Session is" + session.getAttribute("role") + "\n" + (String) session.getAttribute("user"));
		if (session != null) {
			session.invalidate();
		}
		logger.info("\n\n*******Current Session is invalidated");
		return "home";
	}

	@RequestMapping("/")
	public String displayHome(HttpServletRequest request) {
		request.getSession().invalidate();
		return "home";
	}
//	@RequestMapping("/unauthorized")
//	public String display(HttpServletRequest request) {
//		request.getSession().invalidate();
//		return "unauthorized";
//	}

	

	// LOGIN
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
		HttpSession session = request.getSession();
		logger.info("*******REDIRECT LOGIN PAGE Current Session is"+session.getId()+"\n"+session.getAttribute("role")+"\n"+session.getAttribute("user"));
		if(session !=null) {
			session.invalidate();
			logger.info("*******Current Session is invalidated");
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSuccess(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
		request.getSession().invalidate();
		String role = request.getParameter("role");
		String username = request.getParameter("emailid");
		String password = request.getParameter("password");
		
		if (role.equalsIgnoreCase("Manager")) {
			boolean result = loginDao.authenticateUser(role, username, password);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);
			session.setAttribute("role", role);
			if (result == true) {
				return "managerhome";
			} else {
				request.setAttribute("error",
						"Please select appropriate role and enter correct credentials to access the system");
				request.getSession().invalidate();
				return "login";
			}
		} else {
			boolean result = loginDao.authenticateUser(role, username, password);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);
			session.setAttribute("role", role);
			if (result == true) {
				return "studenthome";
			} else {
				request.getSession().invalidate();
				request.setAttribute("error",
						"Please select appropriate role and enter correct credentials to access the system");
				return "login";
			}
		}

	}

	@RequestMapping(value = "/managerhome")
	public ModelAndView loginManager(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		logger.info("******* MANAGER HOME Current Session is"+session.getId()+"\n"+session.getAttribute("role")+"\n"+session.getAttribute("user"));
		String role =(String) session.getAttribute("role");
		logger.info("*****ROLE IS"+role);
		if(role.equals("Manager"))
			return new ModelAndView("managerhome");//this was the only statement present here
		else
			return new ModelAndView("loginrequired");//redirect to home or login page
	}

	@RequestMapping(value = "/studenthome")
	public ModelAndView loginStudent(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		logger.info("*******STUDENT HOME Current Session is"+session.getId()+"\n"+session.getAttribute("role")+"\n"+session.getAttribute("user"));
		String role =(String) session.getAttribute("role");
		logger.info("*****ROLE IS"+role);
		if(role.equals("Student"))
		return new ModelAndView("studenthome");//this was the only statement present here
		else
			return new ModelAndView("loginrequired");//redirect to home or login page
	}
	
	// LOGIN ends here

	
	//Manager Registration starts here

	//method to redirect to manager registration page
	@RequestMapping(value = "/registermanager", method = RequestMethod.GET)
	public ModelAndView registerManager(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		Manager manager = new Manager();
		model.addAttribute("manager", manager);
		return new ModelAndView("registermanager");
	}

	//actual manager registration method
	@RequestMapping(value = "/registermanager", method = RequestMethod.POST)
	public String registerManagerSuccess(@Valid @ModelAttribute("manager") Manager manager, BindingResult bindingResult,
			HttpServletRequest request) throws ManagerException {
		if (bindingResult.hasErrors()) {
			System.out.println("\n\nbindingResult.hasErrors() is TRUE" + bindingResult);
			return "registermanager";
		} else {
			String password = request.getParameter("password");
			String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			Manager managerobj = new Manager();
			managerobj.setNuId(manager.getNuId());
			managerobj.setFirstName(manager.getFirstName());
			managerobj.setLastName(manager.getLastName());
			managerobj.setEmailId(manager.getEmailId());
			managerobj.setPassword(newPassword);
			managerobj.setRole("Manager");
			// RegisterDAO registerDao = new RegisterDAO(); //-- NOTE : useful when autowiring was not working
			int res = registerDao.registerManager(managerobj);
			// if res == 1 condition check pending
//email
			try {
                logger.info("Data successfully stored in Manager Table");
                logger.info("Sending confirmation email");
                SendEmail(manager.getEmailId(), manager.getEmailId());
            } catch (EmailException e) {
                e.printStackTrace();
            }
			
			
			
			return "redirect:/";
		}

	}
	// Method to send email
    public void SendEmail(String emailID, String username) throws EmailException {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("webtoolsproject2020@gmail.com", "1q_2w_3e_4r"));
            email.setSSLOnConnect(true);
            email.setFrom("no-reply@msis.neu.edu");
            email.setSubject("Successfully Registered");
            email.setMsg("Registeration successful    " + "\nYour username is:\t" + username);
            email.addTo(emailID);
            email.send();
        } catch (Exception ex) {
            logger.info("Unable to send email");
            System.out.println(ex);
        }
    }

//Manager Registration ends here

//Student Registration starts here

	@RequestMapping(value = "/registerstudent", method = RequestMethod.GET)
	public ModelAndView registerStudent(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return new ModelAndView("registerstudent");
	}

	@RequestMapping(value = "/registerstudent", method = RequestMethod.POST)
	public String registerStudentSuccess(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			HttpServletRequest request) throws StudentException {
		if (bindingResult.hasErrors()) {
			System.out.println("\n\nbindingResult.hasErrors() is TRUE" + bindingResult);
			return "registerstudent";
		} else {
			String password = request.getParameter("password");
			String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			Student studentobj = new Student();
			studentobj.setNuId(student.getNuId());
			studentobj.setFirstName(student.getFirstName());
			studentobj.setLastName(student.getLastName());
			studentobj.setEmailId(student.getEmailId());
			studentobj.setPassword(newPassword);
			studentobj.setRole("Student");
			// RegisterDAO registerDao = new RegisterDAO();// -- useful when autowiring was not working
			int res = registerDao.registerStudent(studentobj);
			// if res == 1 condition check pending
			
			try {
                logger.info("Data successfully stored in Student Table");
                logger.info("Sending confirmation email");
                SendEmail(student.getEmailId(), student.getEmailId());
            } catch (EmailException e) {
                e.printStackTrace();
            }
			
			return "redirect:/";
		}

	}

//Student Registration ends here	

}
