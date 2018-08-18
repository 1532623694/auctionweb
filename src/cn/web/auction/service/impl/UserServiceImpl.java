package cn.web.auction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.web.auction.mapper.AuctionuserMapper;
import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.pojo.AuctionuserExample;
import cn.web.auction.service.UserService;

//记得加service注解
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired//自动注入，在Spring的xml已经扫描了mapper的全部包并加入了容器
	private AuctionuserMapper auctionuserMapper;
	
	@Override
	public Auctionuser login(String username, String pass) {
		//创建查询条件的封装对象
		AuctionuserExample example = new AuctionuserExample();
		//criteria是一个条件的封装类，并且是一个内部类，进行一些值的添加
		AuctionuserExample.Criteria criteria = example.createCriteria();
		//拼接用户条件，精确查询
		criteria.andUsernameEqualTo(username);
		criteria.andUserpasswordEqualTo(pass);
		//通过mapper查询
 		List<Auctionuser> list = auctionuserMapper.selectByExample(example);
 		
 		if(list!=null && list.size()>0){//登录成功
 			return list.get(0);
 		}
		return null;
	}

	@Override
	public void addUser(Auctionuser auctionuser) {
		auctionuserMapper.insert(auctionuser);
	}

}
