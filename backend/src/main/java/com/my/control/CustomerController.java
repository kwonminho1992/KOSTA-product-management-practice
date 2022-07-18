package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.CustomerOracleRepository;
import com.my.repository.CustomerRepository;

public class CustomerController implements Controller {
  public String execute(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String servletPath = request.getServletPath();
    if ("/login".equals(servletPath)) {
      return login(request, response);
    } else if ("/signup".equals(servletPath)) {
      return signup(request, response);
    }
    return null;
  }

  private String login(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    HttpSession session = request.getSession(); // session 선언
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    CustomerRepository customerRepository = new CustomerOracleRepository();

    // 요청전달 데이터를 얻기
    String id = request.getParameter("id");
    String password = request.getParameter("password");

    // business logic 호출
    try {
      customerRepository.selectByIdAndPassword("asd", "asd");
      map.put("status", 1);
      map.put("message", "login succeed");
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "login failed");
      e.printStackTrace();
    }

    // 결과 출력
    String result = mapper.writeValueAsString(map);
    System.out.println("login() in CustomerController : " + result);
    return result;
  }


  private String signup(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");// 응답 형식 설정 (MIME;encoding)
    ObjectMapper mapper = new ObjectMapper(); // 객체를 json 형식으로 바꾸기
    Map<String, Object> map = new HashMap<String, Object>();
    CustomerRepository customerRepository = new CustomerOracleRepository();

    // parameter
    Customer customer = new Customer();
    String id = request.getParameter("id");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String address = request.getParameter("address");
    id = "newId";
    password = "asd";
    name = "name";
    address = "asd";
    customer.setId(id);
    customer.setPassword(password);
    customer.setName(name);
    customer.setAddress(address);
    customer.setBuildingno(1);
    try {
      customerRepository.insert(customer);
      map.put("status", 1);
      map.put("message", "signup succeed");
    } catch (AddException e) {
      map.put("status", 0);
      map.put("message", "signup failed");
      e.printStackTrace();
    }
    // 가입 성공여부 출력 {"status":1 - 성공, 2 - 실패)
    String result = mapper.writeValueAsString(map);
    System.out.println("signup() in CustomerController : " + result);
    return result;
  }
}

