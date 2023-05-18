package com.example.demo.travellerEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
@Component
public class Passanger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String username;
	String fullname;	
	int wallate;
	String password;
	String email;
	int otp;


}
