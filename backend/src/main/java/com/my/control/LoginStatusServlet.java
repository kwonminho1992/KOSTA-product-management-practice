package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginStatusServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public LoginStatusServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> map = new HashMap<String, Object>();


    // session 만들기
    HttpSession session = request.getSession();
    // out.print(session.isNew());
    // out.print(session.getId());
    // out.print(session.getLastAccessedTime());
    String loginedId = (String) session.getAttribute("loginInfo");
    if (loginedId == null) {
      map.put("status", 0);
    } else {
      map.put("status", 1);
    }
    String result = mapper.writeValueAsString(map);
    System.out.println("LoginStatusServlet : " + result);
    out.print(result);
  }

}

