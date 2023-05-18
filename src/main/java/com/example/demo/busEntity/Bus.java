package com.example.demo.busEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.travellerEntity.Passanger;
import com.example.demo.travellerEntity.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor

public class Bus {

	@Id
	String busnumber;
	String busname;
	String busownerid;
	String registereddate;
	

int seats;
	
	String buscategory;//sleeper,seater
	String acnoac;//AC or NON AC
	int totalpassangertravelled;
byte status;//1=running/2=under maintainance/0=standstill
	
}
