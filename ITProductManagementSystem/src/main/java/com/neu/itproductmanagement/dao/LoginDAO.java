package com.neu.itproductmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import com.neu.itproductmanagement.pojo.Manager;
import com.neu.itproductmanagement.pojo.Student;

public class LoginDAO extends Dao{

	public boolean authenticateUser(String role, String emailid, String password) {
		boolean result=false;
		
		 if(role.equals("Manager")) {
			 List<Manager> m = new ArrayList<Manager>();
			 try {
				 beginTransaction();
				 
				 Query q = getSession().createQuery("FROM Manager WHERE email_id=:email");
				 q.setString("email",emailid);
				 m = q.list();
				 if(!m.isEmpty()) {
					 if (BCrypt.checkpw(password, m.get(0).getPassword())) {
		                    result = true;
		                }
					 else {
						 System.out.println("$$$$$$$$you entered wrong password******");
						 //request.setAttribute("error","you entered wrong password");
						 result=false;
					 }
				 }
				 else {
					 System.out.println("$$$$$$$$your entered email id does not exist******");
					 result=false;
				 }
				
				 
			 }
			 catch(HibernateException e) {
				 rollback();
			 }
			 finally {
		            close();
		        }
		 }
		 else if(role.equals("Student")) {
			 List<Student> s = new ArrayList<Student>();
			 try {
				 beginTransaction();
				 
				 Query q = getSession().createQuery("FROM Student WHERE email_id=:email");
				 q.setString("email",emailid);
				 s = q.list();
				 if(!s.isEmpty()) {
					 if (BCrypt.checkpw(password, s.get(0).getPassword())) {
						 result = true;
					 }
					 else {
						 System.out.println("$$$$$$$$you entered wrong password******");
						 result=false;
					 }
				 }
				 else {
					 System.out.println("$$$$$$$$your entered email id does not exist******");
					 result=false;
				 }
			 }
			 catch(HibernateException e) {
				 rollback();
			 }
			 finally {
		            close();
		        }
		 }
		 return result;
		
		
	}
	
	//check loggedin user... so that so unauthorized task is not performed by student or manager
	
}
