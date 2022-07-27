package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.dto.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.CustomerService;

@Controller
public class CustomerController {
  @Autowired
  private CustomerService customerService;


  @PostMapping(produces = "application/json;charset=UTF-8", value = "signup")
  @ResponseBody
  public Map<String, Object> signup(@RequestBody Customer customer)
      throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      customerService.signup(customer);
      map.put("status", 1);
      map.put("message", "signup succeed");
      System.out.println("성공");
    } catch (AddException e) {
      map.put("status", 0);
      map.put("message", "signup failed.");
      System.out.println("실패");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "signup failed.");
      System.out.println("실패");
      e.printStackTrace();
    }
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "idduplicationcheck")
  @ResponseBody
  public Map<String, Object> idDuplicationCheck(
      @RequestParam(name = "id", required = true) String id) throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      Customer customer = customerService.idDuplicationCheck(id);
      if (customer == null && !id.equals("")) {
        map.put("status", 1);
        map.put("message", "사용이 가능한 ID입니다.");
      } else {
        map.put("status", 0);
        map.put("message", "사용이 불가능한 ID입니다.");
      }
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "연결오류. 다시 실행하세요");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "연결오류. 다시 실행하세요");
      e.printStackTrace();
    }
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "login")
  @ResponseBody
  public Map<String, Object> login(@RequestParam(name = "id", required = true) String id,
      @RequestParam(name = "password", required = true) String password, HttpSession session)
      throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();

    System.out.println(id + " / " + password);
    // business logic 호출
    try {
      Customer customer = customerService.login(id, password);
      map.put("status", 1);
      map.put("message", "login succeed");
      session.setAttribute("loginInfo", id);
    } catch (FindException e) {
      map.put("status", 0);
      map.put("message", "login failed");
      e.printStackTrace();
    } catch (Exception e) {
      map.put("status", 0);
      map.put("message", "login failed");
      e.printStackTrace();
    }
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "loginstatus")
  public Map<String, Object> loginStatus(HttpSession session) throws ServletException, IOException {
    Map<String, Object> map = new HashMap<String, Object>();
    String id = (String) session.getAttribute("loginInfo");

    if (id == null) {
      map.put("status", 0);
    } else {
      map.put("status", 1);
    }
    return map;
  }

  @PostMapping(produces = "application/json;charset=UTF-8", value = "logout")
  private String logout(HttpSession session) throws ServletException, IOException {
    session.removeAttribute("loginInfo"); // 속성만 제거, 객체는 살아있음
    String id = (String) session.getAttribute("loginInfo");
    System.out.println("session의 loginInfo attribute 삭제 여부 확인 : " + id);
    // String path = "/frontend/html/css_js_layout.html";
    // RequestDispatcher rd = request.getRequestDispatcher(path); // 페이지를 path로 이동
    // rd.forward(request, response);
    return null;
  }
}
