package cn.web.auction.pojo;

//自定义扩展的pojo类
//如果不想用resultMap 可以使用扩展的pojo类使用resultType----在AuctionCustomMapper.xml
public class AuctionCustom extends Auction {
	private Double auctionprice;
	private String username;

	public Double getAuctionprice() {
		return auctionprice;
	}

	public void setAuctionprice(Double auctionprice) {
		this.auctionprice = auctionprice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
