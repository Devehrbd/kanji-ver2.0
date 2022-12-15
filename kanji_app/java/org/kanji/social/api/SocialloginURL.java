package org.kanji.social.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SocialloginURL {
	private String N_CLIENT_ID = "APU1t3m4Obdif6jYJRSR";
	private String G_CLIENT_ID = "866792557950-qkcmi3hr3bop38te6t6erivdhud0ov39.apps.googleusercontent.com";
	private String K_CLIENT_ID = "5af9654538cb7e4fb7145ffb2389bc71";
	
	//로그인 페이지 구성
	public String getLoginPageURL(String social,String state) throws UnsupportedEncodingException {
		
		Map<String, String> loginParam = loginURLParameter(social);
		
		String loginURL = loginParam.get("basic_url");
		
		if(social.equals("GOOGLE")) {
			loginURL += String.format(loginParam.get("param_url"), loginParam.get("client_id"), loginParam.get("redirect_uri") , state , loginParam.get("scope"));	
		} else {
			loginURL += String.format(loginParam.get("param_url"), loginParam.get("client_id"), loginParam.get("redirect_uri") , state);		
		}
		return loginURL;
	
	}
	
	//세션에 저장하고, 사용할 state생성
	public String getRandomState() {
		return UUID.randomUUID().toString();
	}
	
	//getLoginPageURL 내부적으로 사용하는 Parameter 구성
	private Map<String,String> loginURLParameter(String social) throws UnsupportedEncodingException{
		Map<String, String> loginParam = new HashMap<>();
		
		switch (social) {
		case "GOOGLE" :
			
			loginParam.put("redirect_uri", URLEncoder.encode("http://localhost:8080/social/google", "UTF-8"));
			loginParam.put("basic_url", "https://accounts.google.com/o/oauth2/v2/auth?response_type=code");
			loginParam.put("scope", "https://www.googleapis.com/auth/userinfo.profile");
			loginParam.put("param_url", "&client_id=%s&redirect_uri=%s&state=%s&scope=%s");
			loginParam.put("client_id", G_CLIENT_ID);
			
			break;
			
		case "KAKAO" :	
			
			loginParam.put("redirect_uri", URLEncoder.encode("http://localhost:8080/social/kakao", "UTF-8"));
			loginParam.put("basic_url", "https://kauth.kakao.com/oauth/authorize?response_type=code");
			loginParam.put("param_url", "&client_id=%s&redirect_uri=%s&state=%s");
			loginParam.put("client_id", K_CLIENT_ID);
			
			break;
			
		default:
			
			loginParam.put("redirect_uri", URLEncoder.encode("http://localhost:8080/social/naver", "UTF-8"));
			loginParam.put("basic_url", "https://nid.naver.com/oauth2.0/authorize?response_type=code");
			loginParam.put("param_url", "&client_id=%s&redirect_uri=%s&state=%s");
			loginParam.put("client_id", N_CLIENT_ID);
			
			break;
		}
		
		return loginParam;
	}
	
}
