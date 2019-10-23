package com.example.community.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;

@Service
public class SessionInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("^_^^_^^_^进入preHandle"+request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				System.out.println("cookies" + cookie.getName());
				if (cookie.getName().equals("token")) {
					System.out.println("cookie.getName():" + cookie.getName());
					String token = cookie.getValue();
					System.out.println("token:" + token);
					User user = userMapper.findByToken(token);
					if (user != null) {
						System.out.println("user!=null");
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	

}
