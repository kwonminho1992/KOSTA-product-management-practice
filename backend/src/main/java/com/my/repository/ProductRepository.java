package com.my.repository;

import java.util.List;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;

/**
 * Interface of ProductRepositories
 * @author asus
 *
 */
public interface ProductRepository {
	/**
	 * add product into repository
	 * @param Product product
	 * @throws AddException if there is duplication or don't keep input rule, occurs this exception
	 */
	abstract public void insert(Product product) throws AddException;	
	/**
	 * modify product which is already deposited in repository
	 * @param Product product
	 * @param String modifiedProductName
	 * @param String modifiedProductPrice
	 * @throws AddException if there is duplication or don't keep input rule, occurs this exception
	 */
	abstract public void modify(String modifiedProductNo, String modifiedProductName, int modifiedProductPrice) throws AddException;
	
	/**
	 * select all products in the repository 
	 * @return List
	 * @throws FindException
	 */
	abstract public List<Product> selectAll() throws FindException;
	
	/**
	 * 
	 * @param String productNo
	 * @return Product
	 * @throws FindException
	 */
	abstract public Product selectByProductNo(String productNo) throws FindException;
	
	abstract public List<Product> selectByProductNoOrName(String keyword) throws FindException;
	
}
