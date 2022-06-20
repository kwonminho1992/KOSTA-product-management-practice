package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dto.Product;
import com.my.sql.MyConnection;
public class DuplicationCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DuplicationCheckServlet() {
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Product sample = (Product) request.getAttribute("test");
		System.out.println("duplicationcheck : " + sample);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		StringBuffer url = request.getRequestURL();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		System.out.println("url = " + url);
		System.out.println("contextPath = " + contextPath);
		System.out.println("servletPath = " + servletPath);
		
		String result = "{\"status\": 0, \"message\": \"중복\"}";
		//parameter
		String id = request.getParameter("id");		
		System.out.println("id : " + id);
		
		//db와 연결
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String selectSQL = "SELECT * FROM customer WHERE id = ?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if((id.equals("")) || (id == null)) {
					result = "{\"status\": 0, \"message\": \"ID를 입력하세요\"}";
			} else if(!rs.next()) { // 중복이 아님
				result = "{\"status\": 1, \"message\": \"사용가능한 아이디 입니다\"}";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			String message = e.getMessage();
			result = "{\"status\": 0, \"message\": \"" + message + "\"}";
		} finally {
			MyConnection.close(rs, pstmt, con);
		}
		
		// 가입 성공여부 출력 {"status":1 - 성공, 2 - 실패)
		System.out.println(result);
		out.print(result);
	}

}