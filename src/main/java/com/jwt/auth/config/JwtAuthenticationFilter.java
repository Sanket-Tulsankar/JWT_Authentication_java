package com.jwt.auth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.auth.services.CustomUserDetailsService;
import com.jwt.auth.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		if(header!=null && header.startsWith("Bearer ")) 
		{
			jwtToken=header.substring(7);
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(username);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loadUserByUsername, null,loadUserByUsername.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			} 
			else 
			{
				 System.out.println("Either usernameis null or authentication is null !!");
			}
				 
			
		}
		else
		{ 
			 System.out.println("Either header is null or header do not start with Bearer !!");
		}
		 
		
		filterChain.doFilter(request, response);
	}

}
