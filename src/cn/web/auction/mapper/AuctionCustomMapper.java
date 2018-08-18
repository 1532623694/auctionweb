package cn.web.auction.mapper;

import java.util.List;

import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;

public interface AuctionCustomMapper {

	// 自定义根据主键查询
	public Auction findAuctionAndReordListById(int auctionid);

	// 拍卖结束的商品列表
	public List<AuctionCustom> findAuctionEndtimeList();

	// 拍卖没结束的
	public List<Auction> findAuctionNoEndtimeList();
}
