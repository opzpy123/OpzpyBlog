package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Commodity;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.provider.AliOssProvider;
import com.opzpy123.mypeojectdemo.service.CommodityService;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.PageAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/commodity")
public class CommodityController {

	@Autowired
	private CommodityService commodityService;

	@Autowired
	private UserService userService;
	@Autowired
	private AliOssProvider aliOssProvider;


	@GetMapping("/usersCommodity/{userId}")
	public String selectUsersCommodity(@PathVariable(name = "userId") Long userId, Model model) {
		System.out.println(userId);
		//service查询 查询结果放入model返回前端.
		List<Commodity> commodies = commodityService.selectUsersCommodity(userId);
		System.out.println(commodies);
		model.addAttribute("commodies", commodies);

		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		return "commodity";
	}

	@GetMapping("/deleteCommodity/{commodyId}/{userId}")
	public String deleteCommodity(@PathVariable("commodyId") Long commodyId, @PathVariable("userId") Long userId, HttpServletResponse response) {
		commodityService.deleteCommodity(commodyId);
		String returnMsg = "<script>alert('" + "删除商品成功！" + "');window.location.href='" + "/commodity/usersCommodity/" + userId + "';</script>";
		PageAlert.Alert(returnMsg, response);
		return "redirect:/commodity/usersCommodity/" + userId;
	}

	//页面跳转
	@GetMapping("/addCommody/{userId}")
	public String addCommody(@PathVariable("userId") Long userId, Model model) {
		model.addAttribute("userId", userId);
		return "addOrUpdateCommody";
	}

	//页面跳转
	@GetMapping("/updateCommody/{commodyId}")
	public String updateCommody(@PathVariable("commodyId") Long commodyId, Model model) {
		Commodity commody = commodityService.selectById(commodyId);
		model.addAttribute("commody", commody);
		System.out.println(commody);
		return "addOrUpdateCommody";
	}

	@PostMapping("/addCommody")
	public String addCommodyForm(String userid,String name,String description,MultipartFile image) {
		Commodity commodity = new Commodity();
		URL url = null;
		if (!image.isEmpty()) {
			try {
				url = aliOssProvider.upload(image.getInputStream(), image.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(url==null){
			commodity.setImage("/image/picture/potato.png");
		}else {
			commodity.setImage(url.toString());
		}
		commodity.setUserid(Long.parseLong(userid));
		commodity.setName(name);
		commodity.setDescription(description);
		commodityService.addCommodity(commodity);
		return "redirect:/commodity/usersCommodity/" + commodity.getUserid();
	}

	@PostMapping("/updateCommody")
	public String updateCommodyForm(String id ,String userid,String name,String description,MultipartFile image) {
		Commodity commodity = new Commodity();
		URL url = null;
		if (!image.isEmpty()) {
			try {
				url = aliOssProvider.upload(image.getInputStream(), image.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(url==null){
			commodity.setImage("/image/picture/potato.png");
		}else {
			commodity.setImage(url.toString());
		}
		commodity.setId(Long.parseLong(id));
		commodity.setUserid(Long.parseLong(userid));
		commodity.setName(name);
		commodity.setDescription(description);
		commodityService.updateCommodity(commodity);
		return "redirect:/commodity/usersCommodity/" + commodity.getUserid();
	}
}
