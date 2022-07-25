package com.my.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JspExample {
  @GetMapping("a1")
  public ModelAndView a() {
    ModelAndView mnv = new ModelAndView();
    mnv.addObject("greeting", "HELLO");
    mnv.setViewName("/WEB-INF/jsp/a.jsp");

    return mnv;
  }

  @GetMapping("b1")
  public String b(Model model) {
    model.addAttribute("greeting", "HELLO");
    return "/WEB-INF/jsp/a.jsp";
  }

  @GetMapping("c1") // return "/WEB-INF/jsp/c1.jsp"; 와 같음
  public void c() {}

  @GetMapping(produces = "application/json;charset=UTF-8", value = "d1")
  @ResponseBody
  public String d() {
    String message = "응답내용";
    return message;
    // @ResponseBody가 없는 경우, view name으로 '응답내용'을 반환
    // @ResponseBody가 있는 경우, '응답내용'을 응답함
  }


}
