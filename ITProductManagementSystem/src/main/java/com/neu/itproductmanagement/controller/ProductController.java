package com.neu.itproductmanagement.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.neu.itproductmanagement.dao.ProductDAO;
import com.neu.itproductmanagement.dao.ProductRequestDAO;
import com.neu.itproductmanagement.dao.RegisterDAO;
import com.neu.itproductmanagement.exception.ManagerException;
import com.neu.itproductmanagement.pojo.Manager;
import com.neu.itproductmanagement.pojo.Product;
import com.neu.itproductmanagement.pojo.ProductRequest;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
/* This controller handles - 
 * ----> Manager - Add, Update, View and Delete Product
 * ----> Student - View, search product and just redirection of request product(POST is handled in Student controller
 **/
	@Autowired
	private ProductDAO productDao;
	
	@Autowired
	private ProductRequestDAO productRequestDAO;

	// InitBinder is a preprocessor and is used here to remove white spaces
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// add products... method page to display add products page
	@RequestMapping(value = "/login/managerhome/addproduct", method = RequestMethod.GET)
	public ModelAndView addprpduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Student")) {
			return new ModelAndView("unauthorized");
		} else {
			Product product = new Product();
			model.addAttribute("product", product);
			return new ModelAndView("addproduct");
		}
	}

	// add products... method page to actually add products
	@RequestMapping(value = "/login/managerhome/addproduct", method = RequestMethod.POST)
	public String addProductSuccess(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		logger.info("IN ADD PRODUCT POST SESSION ROLE" + session.getAttribute("role"));
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return "loginrequired";
		} else if (role.equals("Student")) {
			return "unauthorized";
		} else {

			if (bindingResult.hasErrors()) {
				System.out.println("\n\nbindingResult.hasErrors() is TRUE" + bindingResult);
				return "addproduct";
			} else {

				Product productobj = new Product();
				productobj.setProductName(product.getProductName());
				productobj.setQuantity(product.getQuantity());
				productobj.setRentedquantity(0);

				int res = productDao.addproduct(productobj);
				// if res == 1 condition check pending

				return "redirect:/managerhome";
			}
		}

	}

	// update products... method to page update products
	@RequestMapping(value = "/login/managerhome/updateproduct", method = RequestMethod.GET)
	public ModelAndView updateproduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Student")) {
			return new ModelAndView("unauthorized");
		} else {
			Product product = new Product();
			model.addAttribute("product", product);
			return new ModelAndView("updateproduct");
		}
	}

	// update products... method page to actually update products
	@RequestMapping(value = "/login/managerhome/updateproduct", method = RequestMethod.POST)
	public String updateProductSuccess(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return "loginrequired";
		} else if (role.equals("Student")) {
			return "unauthorized";
		} else {

			String id = request.getParameter("id");
			String quantity = request.getParameter("quantity");

			Product productcheck = productDao.getProductById(Long.parseLong(id));
			System.out.println("#########productcheck" + productcheck);
			if (productcheck == null) {
				request.setAttribute("error", "Product does not exist");
				return "updateproduct";
			}
			int res = productDao.updateProduct(id, Integer.parseInt(quantity));
			return "redirect:/managerhome";
		}

	}

	// Manager view products... method to page view products
	@RequestMapping(value = "/login/managerhome/viewproduct", method = RequestMethod.GET)
	public ModelAndView viewAllproduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Student")) {
			return new ModelAndView("unauthorized");
		} else {
			List<Product> productList = productDao.getAllProducts();
			request.setAttribute("productList", productList);
			model.addAttribute("productList", productList);
			return new ModelAndView("viewproducts");
		}
	}
	
	// Manager Delete Product
		@RequestMapping(value = "/login/managerhome/deleteproduct", method = RequestMethod.GET)
		public ModelAndView redirectDeletePage(HttpServletRequest request, HttpServletResponse response, ModelMap map,
				Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return new ModelAndView("loginrequired");
			} else if (role.equals("Student")) {
				return new ModelAndView("unauthorized");
			} else {
			List<Product> productList = productDao.getAllProducts();
			request.setAttribute("productList", productList);
			model.addAttribute("productList", productList);
			return new ModelAndView("deleteproduct");
			}
		}

		@RequestMapping(value = "/login/managerhome/deleteproduct", method = RequestMethod.POST)
		public ModelAndView deleteProduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
				Model model) {
			HttpSession session = request.getSession();
			String role = (String) session.getAttribute("role");
			if (role == null) {
				return new ModelAndView("loginrequired");
			} else if (role.equals("Student")) {
				return new ModelAndView("unauthorized");
			} else {
			
			List<Product> productList = productDao.getAllProducts();
			long productId = Long.parseLong(request.getParameter("id"));
			Product product = productDao.getProductById(productId);
			if (product == null) {
				request.setAttribute("error", "Product does not exist");
				model.addAttribute("productList", productList);
				return new ModelAndView("deleteproduct");
			}
			if (product.getRentedquantity() != 0) {
				request.setAttribute("error", "You cannot delete this product as it is still rented");
				model.addAttribute("productList", productList);
				return new ModelAndView("deleteproduct");
			}
			
//			ProductRequest pr = productRequestDAO.getRequestById(productId);
//			Date currentDate = new Date();
//			String comment = request.getParameter("comments");
//			pr.setApprovaldate(currentDate);
//			pr.setStatus("rejected");
//			pr.setComments(comment);
//			productRequestDAO.updaterequest(pr);

			productDao.deleteProductById(productId);
			return new ModelAndView("managerhome");
			}
		}
	
	
	
//---------------------------------------------------------------------------------------------------------------
	// Student view products... method to page view products
	@RequestMapping(value = "/login/studenthome/viewproduct", method = RequestMethod.GET)
	public ModelAndView studentViewProduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Manager")) {
			return new ModelAndView("unauthorized");
		} else {
		List<Product> productListStudent = productDao.getAllProducts();
		request.setAttribute("productListStudent", productListStudent);
		model.addAttribute("productListStudent", productListStudent);
		return new ModelAndView("studentviewproducts");
		}

	}

	// Student request products... method to page view products in raise request page
	@RequestMapping(value = "/login/studenthome/requestproduct", method = RequestMethod.GET)
	public ModelAndView studentViewAvailableProduct(HttpServletRequest request, HttpServletResponse response,
			ModelMap map, Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Manager")) {
			return new ModelAndView("unauthorized");
		} else {
		List<Product> productListStudent = productDao.getAllProducts();
		request.setAttribute("productListStudent", productListStudent);
		model.addAttribute("productListStudent", productListStudent);
		return new ModelAndView("studentproductrequest");
		}

	}

	// Student...search products... redirected to that page

	@RequestMapping(value = "/login/studenthome/searchproduct", method = RequestMethod.GET)
	public ModelAndView studentRedirectSearchPage(HttpServletRequest request, HttpServletResponse response,
			ModelMap map, Model model) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Manager")) {
			return new ModelAndView("unauthorized");
		} else {
		List<Product> productListStudent = productDao.getAllProducts();
		request.setAttribute("productListStudent", productListStudent);
		model.addAttribute("productListStudent", productListStudent);
		return new ModelAndView("searchproduct");
		}
	}

	// Actual method to display searched product
	@RequestMapping(value = "/login/studenthome/searchproduct", method = RequestMethod.POST)
	public ModelAndView displaySearchProduct(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if (role == null) {
			return new ModelAndView("loginrequired");
		} else if (role.equals("Manager")) {
			return new ModelAndView("unauthorized");
		} else {
			String keyword = request.getParameter("keyword");
			List<Product> myList = productDao.searchProduct(keyword);
			System.out.println("$%^&&&&&&&&&" + myList);
			if (!myList.isEmpty()) {
				for (Product p : myList) {
					System.out.println("m: " + p.getProductName());
				}
				request.setAttribute("keyword", keyword);
				request.setAttribute("mylist", myList);
				model.addAttribute("myList", myList);
				return new ModelAndView("searchproduct");
			} else {
				request.setAttribute("error", "Your searched product does not exist");
				return new ModelAndView("searchproduct");
			}
		}
	}

	

}
