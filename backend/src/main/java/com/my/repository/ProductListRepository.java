package com.my.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

/**
 * repository in ArrayList type
 * @author asus
 *
 */
public class ProductListRepository implements ProductRepository {
	//field
	private List<Product> products;
	//constructor
	public ProductListRepository() {//10개의 index가 제공
		products = new ArrayList<>();
	}
	public ProductListRepository(int size) {//size개수 만큼의 index가 제공
		products = new ArrayList<>(size);
	}
	

	public void insert(Product product) throws AddException {
		products.add(product);
	}	
	
	public void modify(String modifiedProductNo, String modifiedProductName, int modifiedProductPrice) throws AddException {
		try { // modify productName & productPrice
			Product product = selectByProductNo(modifiedProductNo);
			product.setProductName(modifiedProductName);
			product.setProductPrice(modifiedProductPrice);
		} catch (FindException e) { // print exception message by println() method
			System.out.println("FindException : There is no matched productNo in repository.");
		}		
	}
	
	/**
	 * @return all deposited products in the repository
	 */
	public List<Product> selectAll() throws FindException {
		if (products.size() == 0) {  // if no product is deposited, occur FindException
			throw new FindException("FindException : There is no product in repository.");
		} else { // if there is at least 1 product, return products
			return products;
		}
	}
	
	public Product selectByProductNo(String productNo) throws FindException {		
		int i = 0;
//		for (; i < this.products.size(); i++) { 
//			if (productNo.equals(this.products.get(i).getProductNo())) {
//				return products.get(i);
//			}
//		}
		for (Product p : this.products) { //향상된 for loop (자료구조안의 모든 요소를 반복시킴)
			if (productNo.equals(this.products.get(i).getProductNo())) {
				return products.get(i);
			}
		}
		throw new FindException("FindException : productNo ['" + productNo + "'] cannot be found in repository.");
	}
	
	public List<Product> selectByProductNoOrName(String keyword) throws FindException {		
		//ProductNo or productName에 키워드가 포함되어있는 상품을 모두 반환
		List<Product> allSelected = new ArrayList<Product>(); // list to store the products including keyword 
		int i = 0;
		for (; i < products.size(); i++) {
			String productNo = products.get(i).getProductNo();
			String productName = products.get(i).getProductName();
			if (productName.contains(keyword) || productNo.contains(keyword.toUpperCase())) {
				allSelected.add(products.get(i));
			}
		}
		if (i == products.size()) { // 예외발생 : 검색어와 일치하는 상품이 없음
			throw new FindException("FindException : Cannot find matched productNo in repository.");
		}
		return allSelected;	
	}	

}	
