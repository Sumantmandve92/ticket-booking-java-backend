package com.example.demo.busRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.busEntity.File;

public interface FileRepo extends JpaRepository<File, Long>{

}
