package cn.web.auction.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.web.auction.mapper.AuctionCustomMapper;
import cn.web.auction.mapper.AuctionMapper;
import cn.web.auction.mapper.AuctionrecordMapper;
import cn.web.auction.pojo.Auction;
import cn.web.auction.pojo.AuctionCustom;
import cn.web.auction.pojo.AuctionExample;
import cn.web.auction.pojo.Auctionrecord;
import cn.web.auction.pojo.AuctionrecordExample;
import cn.web.auction.pojo.AuctionuserExample;
import cn.web.auction.service.AuctionService;
import cn.web.auction.utils.RecordException;

@Service
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	// 自动注入，在Spring的xml已经扫描了mapper的全部包并加入了容器
	private AuctionMapper auctionMapper;

	@Autowired
	private AuctionCustomMapper auctionCustomMapper;

	@Autowired
	private AuctionrecordMapper recordMapper;

	@Override
	public List<Auction> findAuctions(Auction auction) {
		// 创建查询条件的封装对象
		AuctionExample example = new AuctionExample();
		AuctionExample.Criteria criteria = example.createCriteria();
		if (auction != null) {
			// 商品名称：模糊查询
			if (auction.getAuctionname() != null
					&& !"".equals(auction.getAuctionname())) {
				criteria.andAuctionnameLike("%" + auction.getAuctionname()
						+ "%");
			}
			// 商品描述：模糊查询
			if (auction.getAuctiondesc() != null
					&& !"".equals(auction.getAuctiondesc())) {
				criteria.andAuctiondescLike("%" + auction.getAuctiondesc()
						+ "%");
			}
			// 开始时间：大于
			if (auction.getAuctionstarttime() != null) {
				criteria.andAuctionstarttimeGreaterThan(auction
						.getAuctionstarttime());
			}
			// 结束时间：小于
			if (auction.getAuctionendtime() != null) {
				criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
			}
			// 起拍价：大于
			if (auction.getAuctionstartprice() != null) {
				criteria.andAuctionstartpriceGreaterThan(auction
						.getAuctionstartprice());
			}
		}
		// 排序后，返回list
		example.setOrderByClause("auctionstarttime desc");
		List<Auction> list = auctionMapper.selectByExample(example);
		return list;
	}

	@Override
	public void addAuction(Auction auction) {
		auctionMapper.insert(auction);
	}

	@Override
	public void updateAuction(Auction auction) {
		auctionMapper.updateByPrimaryKey(auction);
	}

	@Override
	public Auction getAuctionById(int auctionid) {
		return auctionMapper.selectByPrimaryKey(auctionid);
	}

	@Override
	public Auction findAuctionAndRecordListById(int auctionid) {
		return auctionCustomMapper.findAuctionAndReordListById(auctionid);
	}

//	/**
//	 * 1. 
//	 * 	商品竞拍时间没过期 
//	 * 2. 
//	 * 	无商品竞价记录，要高于商品起拍价 
//	 * 	有商品竞价记录，要高于商品的所有竞拍价
//	 * @throws Exception
//	 */
//	@Override
//	public void addAuctionRecord(Auctionrecord auctionrecord) throws Exception {
//		Auction auction = auctionCustomMapper
//				.findAuctionAndReordListById(auctionrecord.getAuctionid());
//		if (auction.getAuctionendtime().after(new Date()) == false) {// 商品的竞拍时间过期
//			throw new Exception("竞拍时间已经过期");
//		} else {// 商品竞拍时间没有过期,判断是否有竞拍记录
//			if (auction.getAuctionrecord() != null
//					&& auction.getAuctionrecord().size() > 0) {// 有竞拍记录
//				// 高于竞拍最高价
//				Auctionrecord maxRecord = auction.getAuctionrecord().get(0);
//				if (auctionrecord.getAuctionprice() < maxRecord
//						.getAuctionprice()) {
//					throw new Exception("商品竞拍价要高于所有竞拍价");
//				}
//			} else {// 无竞拍记录
//					// 高于起拍价
//				if (auctionrecord.getAuctionprice() < auction
//						.getAuctionstartprice()) {
//					throw new Exception("商品竞拍价要高于起拍价");
//				}
//			}
//		}
//		recordMapper.insert(auctionrecord);
//	}

	/**
	 * 1. 
	 * 	商品竞拍时间没过期 
	 * 2. 
	 * 	无商品竞价记录，要高于商品起拍价 
	 * 	有商品竞价记录，要高于商品的所有竞拍价
	 * @throws Exception
	 */
	@Override
	public void addAuctionRecord(Auctionrecord auctionrecord) throws RecordException {
		Auction auction = auctionCustomMapper
				.findAuctionAndReordListById(auctionrecord.getAuctionid());
		if (auction.getAuctionendtime().after(new Date()) == false) {// 商品的竞拍时间过期
			throw new RecordException("竞拍时间已经过期");
		} else {// 商品竞拍时间没有过期,判断是否有竞拍记录
			if (auction.getAuctionrecord() != null
					&& auction.getAuctionrecord().size() > 0) {// 有竞拍记录
				// 高于竞拍最高价
				Auctionrecord maxRecord = auction.getAuctionrecord().get(0);
				if (auctionrecord.getAuctionprice() < maxRecord
						.getAuctionprice()) {
					throw new RecordException("商品竞拍价要高于所有竞拍价");
				}
			} else {// 无竞拍记录
					// 高于起拍价
				if (auctionrecord.getAuctionprice() < auction
						.getAuctionstartprice()) {
					throw new RecordException("商品竞拍价要高于起拍价");
				}
			}
		}
		recordMapper.insert(auctionrecord);
	}

	@Override
	public List<AuctionCustom> findAuctionEndtimeList() {
		return auctionCustomMapper.findAuctionEndtimeList();
	}

	@Override
	public List<Auction> findAuctionNoEndtimeList() {
		return auctionCustomMapper.findAuctionNoEndtimeList();
	}

	@Override
	public void removeAuction(int auctionid) {
		//删除子表Auctionrecord,级联删除
		AuctionrecordExample example = new AuctionrecordExample();
		AuctionrecordExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(auctionid);//根据外键删除
		recordMapper.deleteByExample(example);
		//删除主表Auction
		auctionMapper.deleteByPrimaryKey(auctionid);
	}
	
}
