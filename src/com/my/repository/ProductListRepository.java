package com.my.repository;

import java.util.ArrayList;
import java.util.List;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public class ProductListRepository {
	//field
	private List<Product> products;
	//constructor
	public ProductListRepository() {//10개의 index가 제공
		products = new ArrayList<>();
	}
	public ProductListRepository(int size) {//size개수 만큼의 index가 제공
		products = new ArrayList<>(size);
	}
	
	
	/**
	 * add products into repository
	 * @param product
	 */
	public void insert(Product product) throws AddException {
		String productNo = product.getProductNo();
		String productName = product.getProductName();
		int productPrice = product.getProductPrice();
		//assess input rule		
		boolean assessResult = assessInputRule(product, productNo, productName, productPrice);
		if (assessResult == true) { //insert product
			products.add(product);
		}
	}
	
	/**
	 * modify product of the repository
	 * @param Product product, String modifiedProductNo, String modifiedProductName, int modifiedProductPrice
	 */
	public void modify(Product product, String modifiedProductNo, String modifiedProductName, int modifiedProductPrice) throws AddException {

		//assess input rule		
		boolean assessResult = assessInputRule(product, modifiedProductNo, modifiedProductName, modifiedProductPrice);
		if (assessResult == true) { //modify product
			product.setProductNo(modifiedProductNo);
			product.setProductName(modifiedProductName);
			product.setProductPrice(modifiedProductPrice);
		}		
	}
	
	/**
	 * modify product of the repository
	 * @param Product product, String modifiedProductName, int modifiedProductPrice
	 */
	public void modify(Product product, String modifiedProductName, int modifiedProductPrice) throws AddException {

		//assess input rule		
		String modifiedProductNo = "FFFFF"; //temporary productNo 
		boolean assessResult = assessInputRule(product, modifiedProductNo, modifiedProductName, modifiedProductPrice);
		if (assessResult == true) { //modify product
			product.setProductName(modifiedProductName);
			product.setProductPrice(modifiedProductPrice);
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
	
	/**
	 * print the product's information through user's argument input(productNo)
	 * @param productNo
	 * @return product's productNo, productNo, productName, productPrice, productInfo, productMfd;
	 * @throws FindException
	 */
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
	
	// input assess when add or modify product
	private boolean assessInputRule (Product product, String productNo, String productName, int productPrice) throws AddException{
		int i = 0;
		if (productNo.equals("") || productName.equals("")) { // not allow blank of productNo, productName
			throw new AddException("AddException : productNo and productName should not be blank");
		} else if (!((int) productNo.charAt(i) == 68) && !((int) productNo.charAt(i) == 70) && !((int) productNo.charAt(i) == 71)) { // productNo should start with D, F or G
			throw new AddException("AddException : productNo should start with D, F or G");
		} else if (productPrice <= 0) { // not allow productPrice == 0 or minus number
			throw new AddException("AddException : productPrice should be at least 1. Not allowed 0 and minus");
		} else { //if pass input rule assess, proceed duplication assess
			for (; i < this.products.size(); i++) { // duplication assess
				if (productNo.equals(this.products.get(i).getProductNo())) { // not allow productNo duplication
					break;
				}
			}
			if (i == this.products.size()) { // if there is no duplication, add products
				return true;
			} else {
				throw new AddException("AddException : You can't deposit "
						+ "[productNo='" + product.getProductNo() + "']. the product already exists.");
			}
		}		
	}

}	

