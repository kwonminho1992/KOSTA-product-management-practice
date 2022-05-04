package com.my.repository;

import java.util.ArrayList;
import java.util.List;

import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;

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
		int i = 0;
		for (; i < this.products.size(); i++) {
			if (product.equals(this.products.get(i))) {
				break;
			}
		}
		if (i == this.products.size()) {
			products.add(product);
		} else {
			throw new AddException("You can't deposit "
					+ "[productNo='" + product.getProductNo() + "']. the product already exists.");
		}
	}
	
	/**
	 * @return all deposited products in the repository
	 */
	public List<Product> selectAll() throws FindException {
		if (products.size() == 0) {  // if no product is deposited, occur FindException
			throw new FindException("There is no product in repository.");
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
		throw new FindException("productNo ['" + productNo + "'] cannot be found in repository.");
	}
}	

