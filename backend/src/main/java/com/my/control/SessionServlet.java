package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.dto.Product;
import com.my.exception.FindException;
import com.my.sql.MyConnection;

public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SessionServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//session (클라이언트 별) 객체 얻기
		HttpSession session = request.getSession();
		
		out.print(session.isNew());
		out.print(session.getId());
		out.print(session.getLastAccessedTime());
		
		String loginedId = (String) session.getAttribute("loginInfo");
		if(loginedId == null) {
			out.print("<hr>");
			out.print("로그인 실패");
		} else {
			out.print("<hr>");
			out.print("로그인 성공");			
		}
		
	}	

}
