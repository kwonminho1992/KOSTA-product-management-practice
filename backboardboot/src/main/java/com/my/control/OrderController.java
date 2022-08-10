package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.my.dto.OrderInfo;
import com.my.dto.OrderLine;
import com.my.dto.Product;
import com.my.dto.ResultBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.service.OrderService;

@RestController
@RequestMapping("order/*")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping("addorder")
  public Map<String, Object> addOrder(@RequestBody OrderInfo orderInfo, HttpSession session)
      throws ServletException, IOException {
    Map<Product, Long> cart = (Map<Product, Long>) session.getAttribute("cart");
    if (cart == null) {
      cart = new HashMap<Product, Long>();
      session.setAttribute("cart", cart);
    }
    List<OrderLine> orderLines = orderInfo.getOrderLines();
    for (OrderLine orderLine : orderLines) {
      Product product = orderLine.getOrderProduct();
      Long orderQuantity = orderLine.getOrderQuantity();
      cart.put(product, orderQuantity);
    }

    Map<String, Object> map = new HashMap<>();
    if (cart == null || cart.size() == 0) {// 장바구니없거나 비어있는 경우
      map.put("status", -1);
      map.put("message", "주문실패: 장바구니가 비었습니다");
    } else {
      // 로그인된 사용자인가 확인
      String loginedId = (String) session.getAttribute("loginInfo");
      if (loginedId == null) { // 로그인 안한 사용자인 경우
        map.put("status", 0);
        map.put("message", "로그인하세요");
      } else {
        try {
          orderService.addOrder(orderInfo);// 주문추가
          session.removeAttribute("cart");// 장바구니 비우기
          map.put("status", 1);
          map.put("messsge", "주문성공");
        } catch (AddException e) {
          map.put("status", -1);
          map.put("message", "주문실패: " + e.getMessage());
        } catch (Exception e) {
          map.put("status", -1);
          map.put("message", "주문실패: " + e.getMessage());
        }
      }
    }
    return map;
  }

  @GetMapping("vieworder")
  public ResultBean<List<OrderInfo>> viewOrder(HttpSession session) {
    ResultBean<List<OrderInfo>> resultBean = new ResultBean<List<OrderInfo>>();
    String loginedId = (String) session.getAttribute("loginInfo");
    if (loginedId == null) {
      resultBean.setStatus(0);
      resultBean.setMessage("로그인하세요.");
    } else {
      try {
        List<OrderInfo> orderInfos = orderService.viewOrder(loginedId);
        resultBean.setStatus(1);
        resultBean.setMessage("주문을 불러왔습니다.");
        resultBean.setT(orderInfos);
      } catch (FindException e) {
        e.printStackTrace();
        resultBean.setStatus(-1);
        resultBean.setMessage(e.getMessage());
      }
    }
    return resultBean;
  }
}
