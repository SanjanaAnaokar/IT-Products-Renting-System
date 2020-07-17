package com.neu.itproductmanagement.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.neu.itproductmanagement.dao.ProductDAO;
import com.neu.itproductmanagement.dao.ProductRequestDAO;
import com.neu.itproductmanagement.pojo.Product;
import com.neu.itproductmanagement.pojo.ProductRequest;
import com.neu.itproductmanagement.pojo.Student;

@Controller
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	/* This controller handles - 
	 * ----> Student - POST method of raise request, Track request
	 **/
	
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

	// product request... method page to raise request
	@RequestMapping(value = "/login/studenthome/raiserequest", method = RequestMethod.POST)
	public String requestProduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return "loginrequired";
		} else if (role.equals("Manager")) {
			return "unauthorized";
		} else {
	
		logger.info("In POST STUDENT RAISE REQUEST ");
		Long pid = Long.parseLong(request.getParameter("id"));
		Product product = productDAO.getProductById(pid);
		System.out.println("PRODUCT"+product);
		if(product == null) {
		 request.setAttribute("error", "Product does not exists");
		 List<Product> productListStudent = productDAO.getAllProducts();
		 model.addAttribute("productListStudent",productListStudent);
		 return "studentproductrequest";
		}
		String sname = (String) request.getSession().getAttribute("user");
		System.out.println("SID"+sname);
		Student student = productRequestDAO.getStudentById(sname);
	
		Date currentDate = new Date();
		System.out.println("Date:"+currentDate);
		ProductRequest probj = new ProductRequest();
		probj.setProduct(product);
		probj.setStudent(student);
		probj.setStatus("pending");
		probj.setRequestdate(currentDate);
		
		productRequestDAO.save(probj);
		return "redirect:/studenthome";
		
		}
	}
	
	//method to track its own request... return student track request page
		@RequestMapping(value = "/login/studenthome/trackrequest")
		public String viewRequestHistory(HttpServletRequest request, HttpServletResponse response, ModelMap map, Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return "loginrequired";
			} else if (role.equals("Manager")) {
				return "unauthorized";
			} else {
			List<ProductRequest> productrequestList = productRequestDAO.getAllRequests();
			model.addAttribute("productrequestList", productrequestList);
			return "studenttrackrequest";
			}
		}
	
	
	
}
