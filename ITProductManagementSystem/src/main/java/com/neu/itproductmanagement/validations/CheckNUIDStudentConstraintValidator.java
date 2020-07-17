package com.neu.itproductmanagement.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.itproductmanagement.dao.RegisterDAO;
import com.neu.itproductmanagement.pojo.Student;

public class CheckNUIDStudentConstraintValidator implements ConstraintValidator<CheckNUIDStudent, String> {
	@Autowired
	RegisterDAO registerDao;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		List<Student> allstudents= new ArrayList<Student>();
		
		boolean result= true;
		try {
			 allstudents = registerDao.getAllStudents();
			
			for(Student s: allstudents) {
				if(s.getNuId().equalsIgnoreCase(value)) {
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
