package cn.web.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.web.auction.mapper.AuctionMapper;
import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;
import cn.web.auction.pojo.Auctionrecord;
import cn.web.auction.utils.RecordException;

public interface AuctionService {
	public List<Auction> findAuctions(Auction auction);

	public void addAuction(Auction auction);

	public void updateAuction(Auction auction);

	public Auction getAuctionById(int auctionid);

	public Auction findAuctionAndRecordListById(int auctionid);

	public void addAuctionRecord(Auctionrecord auctionrecord)
			throws RecordException;

	public List<AuctionCustom> findAuctionEndtimeList();

	public List<Auction> findAuctionNoEndtimeList();
	
	public void removeAuction(int auctionid);
 }
