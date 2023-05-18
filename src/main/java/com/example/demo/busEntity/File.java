package com.example.demo.busEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor

public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	String name;
	String type;
	@Column(name = "filebyte", length = 1000)
	byte[] filebyte;
}
