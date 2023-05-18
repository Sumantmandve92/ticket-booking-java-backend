package com.example.demo.busEntity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor

public class BusRoute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@OneToOne
	Bus bus;
	String fromstation;
	String takeoffat;
	String droppingstation;//time/station/time/station like this
	String boardingstation;//time/station/time/station like this
	String landon;
	int ticketprice;
	String tostation;
	String date;
	String[] passangers; 


}
