package com.cy.py.digest;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class md5 {
	@Test
	void testMd5() {
		String s1="12345";
		String pwd = DigestUtils.md5DigestAsHex(s1.getBytes());
		String asHex = DigestUtils.md5DigestAsHex(pwd.getBytes());
		System.out.println(pwd);
		System.out.println(asHex);
	}
	@Test
	void testMd502() {
		String s1="12345";
		String salt=UUID.randomUUID().toString();//使用盐值 增加破解难度
		String pwd = DigestUtils.md5DigestAsHex(s1.getBytes());
		String asHex = DigestUtils.md5DigestAsHex((pwd+salt).getBytes());//盐值加密
		System.out.println(pwd);
		System.out.println(asHex);
	}
	@Test
	void testBase64() {
		/*
		 * 可逆加密
		 */
		String s1="123456";
		Encoder encoder = Base64.getEncoder();
		String s2 =new String(encoder.encode(s1.getBytes()));//加密
		System.out.println("s2="+s2);
		Decoder decoder=Base64.getDecoder(); //解密
		String s3 = new String(decoder.decode(s2));
		System.out.println(s2);
		System.out.println(s3);
		
	}
}
