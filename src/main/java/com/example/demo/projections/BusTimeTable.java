package com.example.demo.projections;

public interface BusTimeTable {

	int getId();
	String getFromstation();
	String getLandon();
	String getTakeoffat();
	String getTostation();
}
//SELECT id,fromstation,landon,takeoffat,tostation FROM bus_route where date>='2022-12-20' and bus_busnumber='MH23JH1'