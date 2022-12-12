package org.kanji.common.controller;

import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.member.entity.Member;
import org.kanji.member.service.MemberServiceImpl;
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
	
	private String N_CLIENT_ID = "APU1t3m4Obdif6jYJRSR";
	
	private String G_CLIENT_ID = "866792557950-qkcmi3hr3bop38te6t6erivdhud0ov39.apps.googleusercontent.com";

	private String K_CLIENT_ID = "5af9654538cb7e4fb7145ffb2389bc71";
	
	@GetMapping("/loginPage")
	public void loginPage(HttpSession session, Model model) throws Exception {
		
		//naver
		String N_redirectURI = URLEncoder.encode("http://localhost:8080/social/naver", "UTF-8");
		String N_state = UUID.randomUUID().toString();
		String N_loginURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		N_loginURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", N_CLIENT_ID, N_redirectURI, N_state);
		session.setAttribute("N_state", N_state);
		model.addAttribute("N_loginURL", N_loginURL);
		
		
		//google
		String G_redirectURI = URLEncoder.encode("http://localhost:8080/social/google", "UTF-8");
		String G_state = UUID.randomUUID().toString();
		String G_loginURL = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code";
		String G_scope = "https://www.googleapis.com/auth/userinfo.profile";
		G_loginURL += String.format("&client_id=%s&redirect_uri=%s&scope=%s&state=%s", G_CLIENT_ID, G_redirectURI, G_scope, G_state);
		session.setAttribute("G_state", G_state);
		model.addAttribute("G_loginURL", G_loginURL);
		
		//kakao
		String K_redirectURI = URLEncoder.encode("http://localhost:8080/social/kakao", "UTF-8");
		String K_state = UUID.randomUUID().toString();
		String K_loginURL = "https://kauth.kakao.com/oauth/authorize?response_type=code";
		K_loginURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", K_CLIENT_ID, K_redirectURI, K_state);
		session.setAttribute("K_state", K_state);
		model.addAttribute("K_loginURL", K_loginURL);
		
	}
	
	//로긴
//	@PostMapping("/login")
//	public String login(Member member, HttpServletRequest request) {
//		
//		HttpSession session = request.getSession();
//		
//		Optional<Member> rt_member = mService.getMember(member);
//		
//		if(rt_member.isPresent()) {
//			
//			if(rt_member.get().getMemberPass().equals(member.getMemberPass())) {
//				
//				session.setAttribute("login_member", rt_member.get());
//				session.setAttribute("login_member_id", rt_member.get().getMemberId());
//				
//				
//				return "redirect:/course/select";
//				
//			}
//			
//		}
//		
//		
//		return "/main";
//	}
	
	
	//가입
	
	@GetMapping("/join")
	public void join() {
	}
	
	@PostMapping("/joinb")
	public String joinb(Member member) {
	
		mService.joinMember(member);
		
		return "/main";
		
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/member/loginPage";
	}
}
