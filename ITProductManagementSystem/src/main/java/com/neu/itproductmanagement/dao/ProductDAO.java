package com.neu.itproductmanagement.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.neu.itproductmanagement.exception.ManagerException;
import com.neu.itproductmanagement.pojo.Manager;
import com.neu.itproductmanagement.pojo.Product;

public class ProductDAO extends Dao {

	public int addproduct(Product productobj) {
		// TODO Auto-generated method stub
		int result = 0;
		beginTransaction();
		getSession().save(productobj);
		commit();
		close();
		return result;
	}

	public List<Product> getAllProducts() {
		List<Product> productlist = new ArrayList<Product>();
		try {
			beginTransaction();
			Query q = getSession().createQuery("FROM Product");
			productlist = q.list();
			commit();
		} catch (HibernateException e) {
			rollback();
			// throw new ManagerException("Could not get Managers " + e);
		} finally {
			close();
		}
		return productlist;
	}

	// Manager uses this method to update products total quantity
	public int updateProduct(String id, int quantity) {

		int result = 0;
		int currentquantity = 0;
		try {
			// to save this object in database we need hibernate session --> to get session
			// we need hibernate configuration
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :id");
			q.setLong("id", Long.parseLong(id));
			Product productUpdate = (Product) q.uniqueResult();
			currentquantity = productUpdate.getQuantity();
			System.out.println(productUpdate);
			if (productUpdate != null) {
				productUpdate.setQuantity(currentquantity + quantity);
				getSession().update(productUpdate);
				commit();
				result = 1;
			}
			System.out.println("In update of product dao result is" + result);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return result;
	}

	public Product getProductById(Long pid) {
		Product prodobj = null;
		try {
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :id");
			q.setLong("id", pid);
			prodobj = (Product) q.uniqueResult();
//            commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return prodobj;

	}
	
	
	//Check Product Availability for renting
	public boolean checkAvailability(String id) {

		boolean available = false;
		int rentedquantity = 0, totalquantity = 0;
		try {
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :id");
			q.setLong("id", Long.parseLong(id));
			Product product = (Product) q.uniqueResult();
			System.out.println(product);
			rentedquantity = product.getRentedquantity();
			totalquantity = product.getQuantity();
			if (rentedquantity + 1 <= totalquantity) {
				available = true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return available;
	}

	// On Approval rented quantity increases below method performs this activity

	public void updateRentedProductCount(long id) {

		int result = 0;
		int rentedquantity = 0, totalquantity = 0;
		try {
			// to save this object in database we need hibernate session --> to get session
			// we need hibernate configuration
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :pid");
			q.setLong("pid", id);
			Product product = (Product) q.uniqueResult();
			rentedquantity = product.getRentedquantity();
			totalquantity = product.getQuantity();
			if (rentedquantity + 1 <= totalquantity) {
				if (product != null) {
					product.setRentedquantity(rentedquantity + 1);
					getSession().update(product);
					commit();
				}
			}
			System.out.println("In rented quantity update of product dao result is" + result);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		//return result;
		
	}
//When product is lost or damaged below method decreases the total product quantity count by 1 and decreases rented quantity by 1
	public void updateTotalAndRentedProductCount(long id) {
		int result = 0;
		int rentedquantity = 0, totalquantity = 0;
		try {
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :pid");
			q.setLong("pid", id);
			Product product = (Product) q.uniqueResult();
			rentedquantity = product.getRentedquantity();
			totalquantity = product.getQuantity();
			if (rentedquantity - 1 >= 0 && totalquantity - 1 >= 0) {
				if (product != null) {
					product.setRentedquantity(rentedquantity - 1);
					product.setQuantity(totalquantity - 1);
					getSession().update(product);
					commit();
				}
			}
			System.out.println("In rented quantity update of product dao result is" + result);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		
	}

	public void updateReturnRentedProductCount(long id) {
		int result = 0;
		int rentedquantity = 0, totalquantity = 0;
		try {
			beginTransaction();
			Query q = getSession().createQuery("from Product where id = :pid");
			q.setLong("pid", id);
			Product product = (Product) q.uniqueResult();
			rentedquantity = product.getRentedquantity();
			totalquantity = product.getQuantity();
			if (rentedquantity - 1  >= 0) {
				if (product != null) {
					product.setRentedquantity(rentedquantity - 1);
					getSession().update(product);
					commit();
				}
			}
			System.out.println("In rented quantity update of product dao result is" + result);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		
	}

	public List<Product> searchProduct(String keyword) {
		List<Product> product = new ArrayList<Product>();
		try {
            beginTransaction();
            Query q = getSession().createQuery(" from Product where productName =:pname");
            q.setString("pname", keyword);
            product = q.list();
            commit();
        } catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return product;
	}

	
	public int deleteProductById(long id) {
        int result = 0;
        try {
            beginTransaction();
            Query q = getSession().createQuery("from Product where id= :pid");
            q.setLong("pid", id);
            Product product = (Product) q.uniqueResult();
            if (product != null) {
                getSession().delete(product);
                commit();
                result = 1;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return result;
    }
	

}
