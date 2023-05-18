package com.example.demo.MSRTCSecurity;

import java.util.Collection;

import com.example.demo.busEntity.BusOwner;
import com.example.demo.jwtSecurity.UserDetails;

public class BusOwnerImplementsUserDetails implements UserDetails{

	BusOwner busowner;
	public BusOwnerImplementsUserDetails(BusOwner busowner) 
	{
		this.busowner=busowner;
	}
	@Override
	public Collection getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return busowner.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return busowner.getUsername();
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
