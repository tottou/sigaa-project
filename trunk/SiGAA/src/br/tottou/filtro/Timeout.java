package br.tottou.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Timeout implements Filter {

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	      HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        HttpSession session = req.getSession(true);
	        String pageRequested = req.getRequestURL().toString();
	        Boolean authenticated = (Boolean) session.getAttribute("authenticated");

	        if (authenticated == null) {
	            authenticated = false;
	        }
	        if (!authenticated && !pageRequested.contains("login")) {     
	            res.setStatus(301);
	            res.sendRedirect(req.getContextPath() + "/login/login.xhtml");                        
	        } else {
	            chain.doFilter(request, response);
	        }
	    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	
		
	}

}
