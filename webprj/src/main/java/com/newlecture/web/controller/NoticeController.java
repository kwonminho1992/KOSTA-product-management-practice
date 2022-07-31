package com.newlecture.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/board/notice/")
public class NoticeController {

  @RequestMapping("list")
  public String list() {
    return "notice.list";
  }

  @RequestMapping("reg")
  @ResponseBody
  public String reg() {
    return "";
  }

  @RequestMapping("edit")
  public String edit() {
    return "notice.detail";
  }
}
