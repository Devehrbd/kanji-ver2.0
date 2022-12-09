package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.member.entity.Member;
import org.kanji.member.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
	
	private MemberServiceImpl mService;
	
	@GetMapping("/loginPage")
	public void loginPage() {
		
	}
	
	//로긴
	@PostMapping("/login")
	public String login(Member member, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Optional<Member> rt_member = mService.getMember(member);
		
		if(rt_member.isPresent()) {
			
			if(rt_member.get().getMemberPass().equals(member.getMemberPass())) {
				
				session.setAttribute("login_member", rt_member.get());
				session.setAttribute("login_member_id", rt_member.get().getMemberId());
				
				
				return "redirect:/course/select";
				
			}
			
		}
		
		
		return "/main";
	}
	
	
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
		return "/member/loginPage";
	}
}
