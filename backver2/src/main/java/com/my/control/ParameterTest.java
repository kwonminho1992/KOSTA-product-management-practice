package com.my.control;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.my.dto.Product;

@Controller
public class ParameterTest {

  @GetMapping("a")
  public void a() {
    System.out.println("call a method");
  }

  @GetMapping("b")
  public void b(HttpServletRequest request) {
    System.out.println(request.getParameter("no"));
  }

  @GetMapping("c")
  public void c(HttpServletResponse response) throws IOException {
    response.sendRedirect("https://www.naver.com");
  }

  @GetMapping("d")
  public void d(HttpSession session) throws IOException {
    System.out.println("session.isNew() : " + session.isNew());
  }

  // http://localhost:8888/back/e?product_no=C0001&product_name=Americano&product_price=1000
  @GetMapping("e")
  public void e(@RequestParam(name = "product_no", required = true) String productNo,
      @RequestParam(name = "product_name", required = false) String productName,
      @RequestParam(name = "product_price", required = false, defaultValue = "0") int productPrice)
      throws IOException {
    System.out.println(productNo + " / " + productName + " / " + productPrice);
  }

  // http://localhost:8888/back/g?product_no=C0001&product_name=Americano&product_price=1000
  @GetMapping("g")
  public void g(Product product) throws IOException {
    System.out.println(product.getProductNo() + " / " + product.getProductName() + " / "
        + product.getProductPrice());
  }

  @PostMapping("i")
  public void i(@RequestBody List<Product> list) {
    for (Product p : list) {
      System.out.println(p);
    }
  }
}

