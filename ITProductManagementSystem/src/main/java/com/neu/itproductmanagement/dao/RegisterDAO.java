package com.neu.itproductmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.neu.itproductmanagement.exception.ManagerException;
import com.neu.itproductmanagement.exception.StudentException;
import com.neu.itproductmanagement.pojo.Manager;
import com.neu.itproductmanagement.pojo.Student;

public class RegisterDAO extends Dao {
	
/* this DAO contains methods to register Manager and Students and get their LIST*/
	
	// For Manager
	public int registerManager(Manager managerobj) {
		// TODO Auto-generated method stub
		int result = 0;
		beginTransaction();
		getSession().save(managerobj);
		commit();
		close();
		return result;

	}

	public List<Manager> getAllManagers() throws ManagerException {
		List<Manager> managerlist = new ArrayList<Manager>();
		try {
			beginTransaction();
			Query q = getSession().createQuery("FROM Manager");
			managerlist = q.list();
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new ManagerException("Could not get Managers " + e);
		} finally {
			close();
		}
		return managerlist;
	}

	// For Manager ends here

	// For Students
	public int registerStudent(Student studentobj) {
		// TODO Auto-generated method stub
		int result = 0;
		beginTransaction();
		getSession().save(studentobj);
		commit();
		close();
		return result;
	}

	public List<Student> getAllStudents() throws StudentException {
		List<Student> studentlist = new ArrayList<Student>();
		try {
			beginTransaction();
			Query q = getSession().createQuery("FROM Student");
			studentlist = q.list();
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new StudentException("Could not get Students " + e);
		} finally {
			close();
		}
		return studentlist;
	}

}
