package com.iktpreobuka.e_diary.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getPassEncoded (String pass) {
		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
		return bCryptPassEncoder.encode(pass);
	}
	
	public static void main(String[] args) {
		System.out.println(getPassEncoded("admin"));
	}
}
