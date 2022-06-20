package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.sql.MyConnection;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");//응답 형식 설정 (MIME;encoding)
		HttpSession session = request.getSession(); // session 선언
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
		Map<String, Object>map = new HashMap<String, Object>(); 
		

		
		//요청전달 데이터를 얻기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		System.out.println("요청전달데이터 id = " + id + "\n요청전달데이터 pwd = " + pwd);
		
		// DB 연결
		Connection con = null;
		PreparedStatement pstmt = null;
		
		// SQL 송신결과
		ResultSet rs = null;
				
		try {
			con = MyConnection.getConnection();
			String selectIdPwdSQL = "SELECT * FROM customer WHERE id=? AND password=?";
			pstmt = con.prepareStatement(selectIdPwdSQL);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if(rs.next()) { // 로그인 성공 - id, pwd 에 대한 정보가 실제로 DB에 있는 경우
				session.setAttribute("loginInfo", id); // loginInfo에 id를 저장시킴
				map.put("status", 1);
			} else {
				map.put("status", 0);
			}
			
			
		} catch (SQLException e) {
		} finally {
			MyConnection.close(rs, pstmt, con);
		}

		//결과 출력
		String result = mapper.writeValueAsString(map);
		System.out.println("LoginServlet : " + result);
		out.print(result);

	}
}