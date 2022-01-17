package com.example.PasswordManagementSys;

import com.example.PasswordManagementSys.Dao.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PasswordManagementSysApplication implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(PasswordManagementSysApplication.class, args);

	}
@Autowired
	AccountRepository accountRepository;
	@Override
	public void run(String... args) throws Exception {
//
	}
}
