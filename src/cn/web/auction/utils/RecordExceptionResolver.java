package cn.web.auction.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class RecordExceptionResolver implements HandlerExceptionResolver {

	//当应用抛出异常，springmvc就会把异常交给RecordExceptionResolver处理
	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		RecordException recordException = null;
		if (arg3 instanceof RecordException) {
			recordException = (RecordException) arg3;
		} else {
			recordException = new RecordException("未知错误");
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", recordException.getMessage());
		mv.setViewName("error");
		return mv;
	}

}
