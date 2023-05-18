package com.example.demo.travellerSecurity;

import java.util.Collection;


import org.springframework.stereotype.Component;

import com.example.demo.jwtSecurity.UserDetails;
import com.example.demo.travellerEntity.Passanger;
@Component
public class PassengerImplementUserDetails  implements UserDetails{

	Passanger passanger;
public	PassengerImplementUserDetails(Passanger passanger)
	{
		this.passanger=passanger;
	}
	@Override
	public Collection getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return passanger.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return passanger.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
