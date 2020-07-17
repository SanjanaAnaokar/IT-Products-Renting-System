package com.neu.itproductmanagement.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.itproductmanagement.dao.RegisterDAO;
import com.neu.itproductmanagement.pojo.Manager;

public class CheckEmailManagerConstraintValidator implements ConstraintValidator<CheckEmailManager, String> {
	@Autowired
	RegisterDAO registerDao;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		List<Manager> allmanagers= new ArrayList<Manager>();
		
		boolean result= true;
		try {
			//RegisterDAO registerDao = new RegisterDAO(); -- created issue of session/entity manager closed
			 allmanagers = registerDao.getAllManagers();
			
			for(Manager m: allmanagers) {
				if(m.getEmailId().equalsIgnoreCase(value)) {
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
