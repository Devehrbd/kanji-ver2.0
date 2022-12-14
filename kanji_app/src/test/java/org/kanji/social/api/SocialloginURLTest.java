package org.kanji.social.api;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class SocialloginURLTest {
	
	@Autowired
	private SocialloginURL social;
	
	@Test
	void getLoginPageURL() throws UnsupportedEncodingException {		
		social.getLoginPageURL("NAVER",social.getRandomState());	
		social.getLoginPageURL("KAKAO",social.getRandomState());	
		social.getLoginPageURL("GOOGLE",social.getRandomState());	
	}
	
	@Test
	void getRandomState() {
		social.getRandomState();
	}

}
