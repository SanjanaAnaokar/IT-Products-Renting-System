package com.neu.itproductmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.neu.itproductmanagement.pojo.Product;
import com.neu.itproductmanagement.pojo.ProductRequest;
import com.neu.itproductmanagement.pojo.Student;

public class ProductRequestDAO extends Dao{

	public Student getStudentById(String sname) {
		Student studentobj=null;
		try {
			beginTransaction();
            Query q = getSession().createQuery("from Student where email_id = :name");
            q.setString("name", sname);
            studentobj = (Student) q.uniqueResult();
//            commit();
		}
		catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		return studentobj;
		
	}

	public void save(ProductRequest probj) {
		try {
			beginTransaction();
			getSession().save(probj);
            commit();
		}
		catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		
	}

	public List<ProductRequest> getAllRequests() {
		List<ProductRequest> list = new ArrayList<ProductRequest>();
		try {
			beginTransaction();
			Query q = getSession().createQuery("from ProductRequest");
            list = q.list();
			commit();
		}
		catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		
		return list;
		
	}

	public ProductRequest getRequestById(long requestId) {
		// TODO Auto-generated method stub
		List<ProductRequest> list = new ArrayList<ProductRequest>();
		try {
			beginTransaction();
			Query q = getSession().createQuery("from ProductRequest where id=:rid");
			q.setLong("rid", requestId);
            list = q.list();
            System.out.println("LIST VALUE"+list);
			commit();
		}
		catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		if(list.isEmpty()) {
			return null;
		}
		 return list.get(0);
	}

	public void updaterequest(ProductRequest pr) {
		// TODO Auto-generated method stub
		try {
			beginTransaction();
			getSession().update(pr);
			commit();
		}
		catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		
		
	}

	
	
}
