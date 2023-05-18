package com.example.demo.travellerEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
public class Profile {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String fullname;
	String dob;
	String aadharno;
	String panno;
	
}
