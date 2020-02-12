package com.istikis.masajes.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD }, urlPatterns = { "/vistas/*" })
public class JspFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		request.getRequestDispatcher(req.getRequestURI().replace(req.getContextPath(), "/WEB-INF")).forward(request,
				response);

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
