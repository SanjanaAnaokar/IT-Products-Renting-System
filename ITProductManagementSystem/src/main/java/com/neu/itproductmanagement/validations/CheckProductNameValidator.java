package com.neu.itproductmanagement.validations;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.neu.itproductmanagement.dao.ProductDAO;
import com.neu.itproductmanagement.pojo.Product;

public class CheckProductNameValidator implements ConstraintValidator<CheckProductName, String> {
	@Autowired
	ProductDAO productDao;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		List<Product> allproducts= new ArrayList<Product>();
		
		boolean result= true;
		try {
			//RegisterDAO registerDao = new RegisterDAO(); -- created issue of session/entity manager closed
			allproducts = productDao.getAllProducts();
			
			for(Product p: allproducts) {
				if(p.getProductName().equalsIgnoreCase(value)) {
					result = false ;
					break;
				}else {
					result = true;
				}
				
			}
			
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			
			
		}
		return result;
	}
}
