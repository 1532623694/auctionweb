package cn.web.auction.utils;

import java.text.SimpleDateFormat;
import org.springframework.core.convert.converter.Converter;
import java.text.ParseException;
import java.util.Date;

public class DataConverter implements Converter<String, Date> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	public Date convert(String time) {
		try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
	}
}
