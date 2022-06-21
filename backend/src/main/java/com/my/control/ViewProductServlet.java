package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.repository.ProductOracleRepository;
import com.my.repository.ProductRepository;

public class ViewProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ViewProductServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		//1. front濡쒕��꽣 productNo 諛쏄린
		String productNo = request.getParameter("product_no");
		System.out.println(productNo);
		//2. DB�뿉�꽌 �긽�뭹 寃��깋
		ProductRepository repository = new ProductOracleRepository();
		String result = "";
		try {
			Product p = repository.selectByProductNo(productNo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 1);
			map.put("message", "해당 상품 없음");
			map.put("p", p);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(map);
			System.out.println("result : " + result);
			out.print(result);
			
			//3. request�쓽 �냽�꽦�쓣 �젙�븯湲�
			request.setAttribute("product_no", p.getProductNo());
			System.out.println("getAttribute : " + request.getAttribute("product_no"));
			
		} catch (FindException e) {
			e.printStackTrace();
		} 	 	
			
		
	}
}