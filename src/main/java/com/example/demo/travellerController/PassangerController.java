package com.example.demo.travellerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtSecurity.JwtUtil;
import com.example.demo.jwtSecurity.UserDetails;
import com.example.demo.travellerEntity.Passanger;
import com.example.demo.travellerRepo.PassangerRepo;
import com.example.demo.travellerSecurity.PassengerImplementUserDetails;

@RestController
@CrossOrigin
public class PassangerController {
//	=====================================================================================
	@Autowired
	Passanger passanger;
	@Autowired
	PassangerRepo passRepo;
	@Autowired
	JwtUtil jwt;
//	========================================================================================
//	Handler
	@RequestMapping("registerPassanger")
	public int register(@RequestBody Passanger p1) 
	{
		try {
			byte cnt=passRepo.countByUsername(p1.getUsername());
			if(cnt==0) 
			{
				passRepo.save(p1);
				return 1;
			}
			else 
			{
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping("passangerLogin")
	public KeyWithStatus  passangeLogin(@RequestBody Passanger p1) 
	{
		
		try {
			byte cnt=passRepo.countByUsername(p1.getUsername());
			System.out.println(cnt);
			if(cnt==1) 
			{
				Passanger p2=passRepo.findByUsername(p1.getUsername());
				if(p2.getPassword().equals(p1.getPassword())) 
				{
					//					login successsfully
					passanger.setPassword(p1.getPassword());
					passanger.setUsername(p1.getUsername());
					UserDetails userDetails=new PassengerImplementUserDetails(passanger);
					String tokan=jwt.generateToken(userDetails);
					System.out.println(tokan);
					System.out.println(jwt.extractUsername(tokan));
					return new KeyWithStatus(1,tokan);
				
				}
				else 
				{
					//					wrong password
					return new KeyWithStatus(-1,null);
				}
			}
			else 
			{
				//				wrong username
				return new KeyWithStatus(-2,null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return new KeyWithStatus(0,null);
		}

	}
}
