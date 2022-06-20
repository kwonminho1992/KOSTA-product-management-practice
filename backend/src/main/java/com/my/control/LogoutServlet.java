package com.my.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LogoutServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo"); // 속성만 제거, 객체는 살아있음
		
		String path = "/lo"; 
		RequestDispatcher rd = request.getRequestDispatcher(path); // 페이지를 path로 이동 
		rd.forward(request, response); 
	}
}