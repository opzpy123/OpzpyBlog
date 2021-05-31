package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Commodity;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.service.CommodityService;
import com.opzpy123.mypeojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/commodity")
public class CommodityController {

	//todo 这里commodityService
	@Autowired
	private CommodityService commodityService;

	@Autowired
	private UserService userService;



	@GetMapping("/usersCommodity/{userId}")
	public String selectUsersCommodity(@PathVariable(name = "userId") Long userId, Model model)  {
		System.out.println(userId);
		//service查询 查询结果放入model返回前端.
		List<Commodity> commodies = commodityService.selectUsersCommodity(userId);
		System.out.println(commodies);
		model.addAttribute("commodies",commodies);

		User user = userService.findUserById(userId);
		model.addAttribute("user",user);
		return "commodity";
	}
}
