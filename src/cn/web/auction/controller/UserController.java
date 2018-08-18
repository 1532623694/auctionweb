package cn.web.auction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.web.auction.pojo.AuctionExample;
import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/doLogin")
	public String doLogin(String username, String password,
			String inputCode, HttpSession session, Model mv) {
		// 1.先判断验证码
		if (!inputCode.equals(session.getAttribute("numrand"))) {
			mv.addAttribute("errorMsg", "验证码错误");
			return "login";
		}
		//查询数据库
		Auctionuser loginUser = userService.login(username, password);
		if (loginUser != null) {
			//session中保存用户对象
			session.setAttribute("user", loginUser);
			//跳转到AuctionController的queryAuction方法中，查询数据并跳转到homepage
			//重定向到controller中，redirect
			return "redirect:/auction/queryAuction";// 成功:首页
		} else {// 失败:当前login.jsp
			mv.addAttribute("errorMsg", "账号或密码错误");
			return "login";
		}
	}
	
	//表单提交数据必须加@Validated，这有关数据校验
	@RequestMapping("/register")
	public String register(Model model
			,@Validated @ModelAttribute("registerUser") Auctionuser user
			,BindingResult bindingResult){
		
		//判断校验是否通过
		if(bindingResult.hasErrors()){
//			//全局消息报错
//			List<ObjectError> errors = bindingResult.getAllErrors();
//			for (ObjectError error : errors) {
//				System.out.println(error.getDefaultMessage());
//			}
//			model.addAttribute("errors", errors);
//			return "register";
			
			//输入框错误消息提示
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				//遍历errors，存入model中，键名FieldName
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
			return "register";
		}
		user.setUserisadmin(0);//默认注册的是普通用户
		userService.addUser(user);
		return "login";
		
	}
	
	@RequestMapping("/doLogout")
	public String doLogout(HttpSession session){
		session.invalidate();
		return "login";
	}
}
