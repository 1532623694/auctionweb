package cn.web.auction.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;
import cn.web.auction.pojo.Auctionrecord;
import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.service.AuctionService;
import cn.web.auction.utils.RecordException;

@Controller
@RequestMapping("/auction")
public class AuctionController {

	@Autowired
	private AuctionService auctionService;

	public static final int SHOW_PAGE = 5;

	// @ModelAttribute("condition")数据在页面重新显示(回显)
	@RequestMapping("/queryAuction")
	public ModelAndView queryAuction(
			@ModelAttribute("condition") Auction condition,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ModelAndView mv = new ModelAndView();
		// 设置分页参数
		PageHelper.startPage(pageNum, SHOW_PAGE);
		List<Auction> list = auctionService.findAuctions(condition);
		mv.addObject("auctionList", list);

		// 用PageInfo对结果进行包装
		PageInfo pageInfo = new PageInfo<>(list);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("homepage");//执行拦截器postHandler方法
		return mv;//afterCompletion
	}

	@RequestMapping("/publishAuctions")
	public String publishAuctions(Auction auction,MultipartFile pic
			,HttpSession session){
		try {
			//pic->tomcat目录中
			//指定文件保存目录upload(在服务器端的一个绝对路径)
			//F:\\tomcat\\apache-tomcat-6.0.37\\wtpwebapps\\auctionweb\\upload
			String path=session.getServletContext().getRealPath("upload");
			System.out.println(path);
			//判断是否上传了图片(pic永远不为null)
			if(pic.getSize() > 0){
				File targetFile = new File(path,pic.getOriginalFilename());
				//另存到目录
				pic.transferTo(targetFile);
				//文件名和类型 ，手动设置
				auction.setAuctionpic(pic.getOriginalFilename());
				auction.setAuctionpictype(pic.getContentType());
				
			}
			auctionService.addAuction(auction);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/auction/queryAuction";
	}
	
	@RequestMapping("/toUpdate/{auctionid}")
	public ModelAndView toUpdate(@PathVariable int auctionid){
		Auction auction = auctionService.getAuctionById(auctionid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("auction",auction);
		mv.setViewName("updateAuction");
		return mv;
	}
	
	@RequestMapping("/updateAuctoinSubmit")
	public String updateAuctoinSubmit(Auction auction,MultipartFile pic
			,HttpSession session){
		try {
			String path=session.getServletContext().getRealPath("upload");
			System.out.println(path);
			//判断是否重新上传图片
			if(pic.getSize() > 0){
				//删除旧图片
				File oldFile = new File(path,auction.getAuctionpic());
				if(oldFile.exists()){
					oldFile.delete();
				}
				File targetFile = new File(path,pic.getOriginalFilename());
				//另存到目录
				pic.transferTo(targetFile);
				//文件名和类型 ，手动设置到pojo中
				auction.setAuctionpic(pic.getOriginalFilename());
				auction.setAuctionpictype(pic.getContentType());
				
			}
			auctionService.updateAuction(auction);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/auction/queryAuction";
	}
	
	//处理好mapper.xml+业务方法的绑定 剩下的在controller里面就只用调用就可以了
	@RequestMapping("/toDetail/{auctionid}")
	public ModelAndView toDetail(@PathVariable int auctionid){
		ModelAndView mv = new ModelAndView();
		Auction auction = auctionService.findAuctionAndRecordListById(auctionid);
		mv.addObject("auctionDetail", auction);
		mv.setViewName("auctionDetail");
		return mv;
 	}
	
//	@RequestMapping("/saveAuctionRecord")
//	public String saveAuctionRecord(Auctionrecord auctionrecord,HttpSession session,Model model){
//		try {
//			//从session中获取user的id
//			Auctionuser user = (Auctionuser) session.getAttribute("user");
//			//设置好商品竞拍记录的user id
//			auctionrecord.setUserid(user.getUserid());
//			//设置商品竞拍记录当前时间
//			auctionrecord.setAuctiontime(new Date());
//			
//			auctionService.addAuctionRecord(auctionrecord);
//		} catch (Exception e) {
//			model.addAttribute("errorMsg",e.getMessage());
//			return "error";
//		}
//		return "redirect:/auction/toDetail/"+auctionrecord.getAuctionid();
//	}
	
	//该方法调用service 一旦异常，就由前端控制器dispatcherServlet接受，再交给异常处理器RecordExceptionResolver
	@RequestMapping("/saveAuctionRecord")
	public String saveAuctionRecord(Auctionrecord auctionrecord,HttpSession session,Model model) throws RecordException{
		//从session中获取user的id
		Auctionuser user = (Auctionuser) session.getAttribute("user");
		//设置好商品竞拍记录的user id
		auctionrecord.setUserid(user.getUserid());
		//设置商品竞拍记录当前时间
		auctionrecord.setAuctiontime(new Date());
		
		auctionService.addAuctionRecord(auctionrecord);
		
		return "redirect:/auction/toDetail/"+auctionrecord.getAuctionid();
	}
	
	@RequestMapping("/toAuctionResult")
	public ModelAndView toAuctionResult(){
		ModelAndView mv = new ModelAndView();
		List<AuctionCustom> endList = auctionService.findAuctionEndtimeList();
		List<Auction> noEndList = auctionService.findAuctionNoEndtimeList();
		mv.addObject("auctionCustomList", endList);
		mv.addObject("auctionList", noEndList);
		mv.setViewName("auctionResult");
		return mv;
	}
	
	@RequestMapping("/removeAuction/{auctionid}")
	public String removeAuction(@PathVariable int auctionid){
		auctionService.removeAuction(auctionid);
		return "redirect:/auction/queryAuction";
	}
}
