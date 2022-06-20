package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.repository.ProductOracleRepository;
import com.my.repository.ProductRepository;


public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductListServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		ProductRepository repository = new ProductOracleRepository();	
		List<Product> products;
		try {
			products = repository.selectAll();
		} catch (FindException e) {
			e.printStackTrace();
			products = new ArrayList<Product>();
		}
				
		
		String result = "[";
		for (int i = 0; i < products.size(); i++) {
			result += "{";
			result += "\"productNo\": \"" + products.get(i).getProductNo() + "\",";
			result+= "\"productName\": \"" + products.get(i).getProductName() + "\",";
			result += "\"productPrice\": \"" + products.get(i).getProductPrice() + "\",";
			result += "\"productInfo\": \"" + products.get(i).getProductInfo() + "\",";
			result += "\"productMfd\": \"" + products.get(i).getProductMfd() + "\"";
			result += "}";
			if (i != products.size() - 1) {
				result += ",";
			}	
		}
		result += "]";
		System.out.println("productListServlet : " + result);
		out.println(result);
	}

}