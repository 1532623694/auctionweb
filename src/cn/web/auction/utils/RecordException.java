package cn.web.auction.utils;

//自定义异常
//异常类特性：具有可抛性
public class RecordException extends Exception {
	public String message;

	public RecordException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
