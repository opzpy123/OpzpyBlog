package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.PageAlert;
import com.opzpy123.mypeojectdemo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ContactController {
	@Autowired
	UserService userService;

	@GetMapping("/contact/toPage")
	public String gotoPage(String userId, Model model) {
		System.out.println("跳转");
		System.out.println(userId);

		User user = userService.findUserById(Long.valueOf(userId));
		System.out.println(user);
		model.addAttribute("user", user);
		return "userContact";
	}

	@PostMapping("/contact/addContact")
	public String addContact(String userId, String userName, String userContact, HttpServletResponse response) {
		System.out.println(userId);
		System.out.println(userName);
		System.out.println(userContact);
		boolean isSuccess = userService.updateUserContact(Long.valueOf(userId), userContact);
		if (isSuccess) {
			String returnMsg = "<script>alert('" + "添加联系方式成功！" + "');window.location.href='" + "/" + "';</script>";
			PageAlert.Alert(returnMsg, response);
			return null;
		} else {
			String returnMsg = "<script>alert('" + "添加联系方式失败！" + "');window.location.href='" + "/contact/toPage?userId="+userId + "';</script>";
			PageAlert.Alert(returnMsg, response);
			return null;
		}
	}
}
