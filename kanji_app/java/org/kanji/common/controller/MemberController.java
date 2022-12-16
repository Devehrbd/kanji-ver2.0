package org.kanji.common.controller;

import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.member.entity.Member;
import org.kanji.member.service.MemberServiceImpl;
import org.kanji.social.api.SocialloginURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberServiceImpl mService;
	@Autowired
	private SocialloginURL social;
	
	@GetMapping("/loginPage")
	public void loginPage(HttpSession session, Model model) throws Exception {
		
		
		
		
		
//		//naver 로그인 url 생성
//		String N_state = social.getRandomState();
//		String N_loginURL = social.getLoginPageURL("NAVER", N_state);
//		
//		//google 로그인 url 생성
//		String G_state = social.getRandomState();
//		String G_loginURL = social.getLoginPageURL("GOOGLE", G_state);
//		
//		//kakao 로그인 url 생성
//		String K_state = social.getRandomState();
//		String K_loginURL = social.getLoginPageURL("KAKAO", K_state);
//			
//		// state값 저장
//		session.setAttribute("N_state", N_state);
//		session.setAttribute("G_state", G_state);
//		session.setAttribute("K_state", K_state);
//		
//		// url 앞단
//		model.addAttribute("N_loginURL", N_loginURL);
//		model.addAttribute("K_loginURL", K_loginURL);
//		model.addAttribute("G_loginURL", G_loginURL);
	}
		
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/member/loginPage";
	}
}
