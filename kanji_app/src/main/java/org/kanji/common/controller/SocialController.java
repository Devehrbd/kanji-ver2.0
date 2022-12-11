package org.kanji.common.controller;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.kanji.member.entity.Member;
import org.kanji.member.service.MemberService;
import org.kanji.member.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/social")
public class SocialController {
	
	@Autowired
	private MemberServiceImpl mService;
	private String N_CLIENT_ID = "APU1t3m4Obdif6jYJRSR";
	private String N_CLI_SECRET = "j1QvEmBdBb";

	@RequestMapping("/naverLogin")
	public void getAuthorizationUrl(HttpSession session, Model model) throws Exception{
		
		String redirectURI = URLEncoder.encode("http://localhost:8080/social/naver", "UTF-8");
		String state = UUID.randomUUID().toString();
		String loginURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		loginURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", N_CLIENT_ID, redirectURI, state);
		session.setAttribute("state", state);
		model.addAttribute("loginURL", loginURL);
	
	}
	
	@RequestMapping("/naver")
	public String naver(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception{
		
		String sessionState = (String)session.getAttribute("state");
		if(sessionState.equals(state)) {
			String tempTokenURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
			tempTokenURL += String.format("&client_id=%s&client_secret=%s&code=%s&state=%s",N_CLIENT_ID,N_CLI_SECRET,code,state);
			URL tokenURL = new URL(tempTokenURL);
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(tokenURL.openStream(),"UTF-8"));	
			String info = bf.readLine();
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(info);
			
			String tempProfileURL =  "https://openapi.naver.com/v1/nid/me";
			String headerProfileURL = "Bearer " + object.get("access_token");

			URL profileURL = new URL(tempProfileURL);
			HttpURLConnection httpURL = (HttpURLConnection) profileURL.openConnection();
			
			httpURL.setRequestProperty("Authorization", headerProfileURL);
			
			BufferedReader bf2 = new BufferedReader(new InputStreamReader(httpURL.getInputStream(),"UTF-8"));
			String profileInfo = bf2.readLine();
						
			JSONObject object2 = (JSONObject) parser.parse(profileInfo);
			
			JSONObject object3 = (JSONObject) object2.get("response");
			
			String name = (String) object3.get("name");
			String id = (String) object3.get("id");
			
			Member member = new Member();
			member.setMemberId((String)object3.get("id"));
			member.setLoginMethod("NAVER");
			
			session.setAttribute("login_member_id", id);
			session.setAttribute("login_member", member);
			
			if(mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}
			return "redirect:/course/select";
			
		}
		
		return  "redirect:/main";
	}
	
}