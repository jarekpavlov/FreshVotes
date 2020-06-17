package com.freshvotes.service;

import static org.hamcrest.CoreMatchers.not;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserDetailServiceImplTest {

	@Test
	void test() {

		BCryptPasswordEncoder encriptpassword = new BCryptPasswordEncoder();

		String rawPassword = "asdf";
		String password = encriptpassword.encode(rawPassword);
		System.out.println(password);
		assertThat(password, not(rawPassword));

	}

	private void assertThat(String password, Matcher<String> not) {
		// TODO Auto-generated method stub

	}

}
