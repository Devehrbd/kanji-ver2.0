package org.kanji.common.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.kanji.member.service.MemberServiceImpl;
import org.kanji.security.auth.CustomOAuth2MemberService;
import org.kanji.security.auth.PrincipalDetails;
import org.kanji.tests.TestUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(KanjiController.class)
class KanjiControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MemberRepository mRepo;
	@Autowired
	private TestUserDetailService testService;
	
	private PrincipalDetails principalDetails;
	
	@Before
	public void setup() {
		principalDetails = (PrincipalDetails) testService.loadUserByUsername("아무거나");
	}
	


}
