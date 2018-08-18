package cn.web.auction.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器，工具类，都可以在SpringMVC配置文件配置
 * 配置文件可以设置拦截器拦截的路径，可以定位到某个controller
 * 具体定位可以看github的Spring boot练习，和慕课网那个Spring boot常用整合
 * @author www34
 */
public class CheckUserInterceptor implements HandlerInterceptor {

	//执行handler（处理器 controller）之前执行的
	//返回true：放行 表示可访问的handler方法
	//返回false：拦截
	//应用场景：检测用户是否登录
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
		//检测Httpsession是否有user对象
	 	HttpSession session = req.getSession();
	 	//避免死循环，要判断某些 直接放行的 资源，例如：doLogin、register，无需拦截
	 	//判断请求的路径
	 	String path = req.getRequestURI();
	 	System.out.println("URI"+path);
	 	if (path.indexOf("doLogin")!=-1 || path.indexOf("register")!=-1) {
			return true;
		}
	 	
	 	if (session.getAttribute("user") != null) {//用户已经登录
	 		System.out.println("用户已经登录");
			return true;
		} else {
			System.out.println("用户未登录，已经拦截");
			res.sendRedirect(req.getContextPath()+"/login.jsp");
			return false;
		}
	}

	//执行完handler之后执行的，但还没返回（ModelAndView）
	//应用场景：拦截handler之后，要根据业务逻辑来修改ModelAndView时
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	//执行完handler之后，但是要完全返回ModelAndView，执行时机比postHandle稍微晚一些
	//应用场景：记录日记
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

}
