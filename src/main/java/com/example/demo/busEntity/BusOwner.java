package com.example.demo.busEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
@Component
public class BusOwner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int busownerid;
	String username;
	String password;
	int otp;
	String email;
	String mobilenumber;
	String companyname;
	
}
