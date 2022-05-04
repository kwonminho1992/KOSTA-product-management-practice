package com.my.dto;

import java.util.Date;

/**
 * Object of project
 * --상품--
 * 상품번호 : "F0001"(food), "G0001"(goods), "D0001"(drink) - String
 * 상품명 :  "스콘"           "머그1", "머그2"   "아메리카노" - String
 * 가격  :   1000            2000, 2500      1000 - int
 * 상품상세정보 : "~~"         "~~", "~~"      "~~"  - String
 * 상품제조일자 : 2022/04/20    2022/04/21    2022/04/23 - Date
 * @author kwon minho
 *
 */
public class Product {
	// field
	private String productNo;
	private String productName;
	private int productPrice;
	private String productInfo;
	private Date productMfd;
	// constructor
	public Product(String productNo, String productName, int productPrice) { // 3 constructors
		this (productNo, productName, productPrice, null, null);
	}	
	public Product(String productNo, String productName, int productPrice, Date productMfd) { // 4 constructors
		this (productNo, productName, productPrice, null, productMfd); // without productInfo
	}	
	public Product(String productNo, String productName, int productPrice, String productInfo) { // 4 constructors
		this (productNo, productName, productPrice, productInfo, null); // without productMfd
	}	
	public Product(String productNo, String productName, int productPrice, String productInfo, Date	productMfd) { // full constructors
		this.productNo = productNo;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productInfo = productInfo;
		this.productMfd = productMfd;
	}
	
	/**
	 *  method to print information of the product
	 */
	public void print() {
		System.out.println("상품번호 : " + this.productNo + ", 상품명 : " + this.productName + 
				", 가격 : " + this.productPrice + ", 상세정보 : " + this.productInfo +
				", 제조일자 : " + this.productMfd);
	}
}
