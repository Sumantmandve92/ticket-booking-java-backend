package com.example.demo.jwtSecurity;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.MSRTCSecurity.BusOwnerImplementsUserDetails;
import com.example.demo.busEntity.BusOwner;
import com.example.demo.busRepository.BusOwnerRepo;
import com.example.demo.travellerEntity.Passanger;
import com.example.demo.travellerRepo.PassangerRepo;
import com.example.demo.travellerSecurity.PassengerImplementUserDetails;



public class SecurityGuard  extends OncePerRequestFilter{

//	=================================================================================================
	@Autowired
	JwtUtil jwt;
	@Autowired
	BusOwnerRepo busownerRepo;
	@Autowired
	PassangerRepo passRepo;
//	===================================================================================================
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path=request.getRequestURI();
		if(path.startsWith("/passangerLogin")
		 ||path.startsWith("/registerPassanger")
		 ||path.startsWith("/getTodaysBuses")
		 ||path.startsWith("/busOwnerRegistration")
		 ||path.startsWith("/busOwnerLogin")
		 ||path.startsWith("/bookTicket")
		 ||path.startsWith("/confirmbyotp")) 
		{
			filterChain.doFilter(request, response);
			
		}
		else 
		{
			String[] pathdetails=path.split("/");
			String tokan=pathdetails[pathdetails.length-1];
			String username=jwt.extractUsername(tokan);
			System.out.println(pathdetails[1]+" "+pathdetails[2]);
			if(path.charAt(2)=='2') 
			{
				BusOwner busowner=busownerRepo.findByUsername(username);
				
				UserDetails userDetails=new BusOwnerImplementsUserDetails(busowner);
				if(jwt.validateToken(tokan, userDetails)) 
				{
					request.setAttribute("username", busowner.getUsername());
					filterChain.doFilter(request, response);					
				}
				else 
				{
					throw new IOException();
				}
			}
			else 
			{
				Passanger r1=passRepo.findById(username).get();
				UserDetails userDetails =new PassengerImplementUserDetails(r1);
				if(jwt.validateToken(tokan, userDetails)) 
				{
					request.setAttribute("username", r1.getUsername());
					filterChain.doFilter(request, response);
				}
				else 
				{
					throw new IOException();
				}
			}
		}
		
	}

}
