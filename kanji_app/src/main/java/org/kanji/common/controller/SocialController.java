package org.kanji.common.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
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
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jwt.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
	private String G_CLIENT_ID = "866792557950-qkcmi3hr3bop38te6t6erivdhud0ov39.apps.googleusercontent.com";
	private String G_CLI_SECRET = "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";
	private String K_CLIENT_ID = "5af9654538cb7e4fb7145ffb2389bc71";
	private String K_CLI_SECRET = "EtT52bLhgw9UfPOqfDrXCHzza8YIXikY";
	
	@RequestMapping("/naver")
	public String naver(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception{
		
		String sessionState = (String)session.getAttribute("N_state");
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
	
	@RequestMapping("/google")
	public String google(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception{
		
		String sessionState = (String)session.getAttribute("G_state");
		if(sessionState.equals(state)) {
			String G_redirectURI = URLEncoder.encode("http://localhost:8080/social/google", "UTF-8");
			String tokenParam = String.format("client_id=%s&client_secret=%s&code=%s&state=%s&grant_type=authorization_code&redirect_uri=%s",G_CLIENT_ID,G_CLI_SECRET,code,state,G_redirectURI);
			
			URL tokenURL = new URL("https://oauth2.googleapis.com/token");
			HttpURLConnection con = (HttpURLConnection)tokenURL.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", Integer.toString(tokenParam.length()));
			con.setDoOutput(true);
			con.setDoInput(true);
			
			try (DataOutputStream dos = new DataOutputStream(con.getOutputStream())) {
				dos.writeBytes(tokenParam);
	        }
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String inputLine;
			
			while ((inputLine=br.readLine()) != null) {
				sb.append(inputLine);
			}
	
			String token = sb.toString();
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(token);
			
			String tempProfileURL =  "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
			String headerProfileURL = "Bearer " + object.get("access_token");
			
			URL profileURL = new URL(tempProfileURL);
			HttpURLConnection httpURL = (HttpURLConnection) profileURL.openConnection();
			
			httpURL.setRequestProperty("Authorization", headerProfileURL);
			httpURL.setRequestProperty("aceept", "application/json");
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(httpURL.getInputStream(),"UTF-8"));
			
			StringBuilder sb2 = new StringBuilder();
			String inputLine2;
			
			while ((inputLine2=br2.readLine()) != null) {
				sb2.append(inputLine2);

			}
			
			String profileData = sb2.toString();
			JSONObject object2 = (JSONObject) parser.parse(profileData);
			
			Member member = new Member();
			member.setMemberId(String.valueOf(object2.get("id")));
			member.setLoginMethod("GOOGLE");
			
			session.setAttribute("login_member_id",member.getMemberId());
			session.setAttribute("login_member", member);
			
			if(mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}
			
			return "redirect:/course/select";
			
		}
		
		return  "redirect:/main";
	}
	
	@RequestMapping("/kakao")
	public String kakao(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception{
		
		String sessionState = (String)session.getAttribute("K_state");
		if(sessionState.equals(state)) {
			String K_redirectURI = URLEncoder.encode("http://localhost:8080/social/kakao", "UTF-8");
			String tokenParam = String.format("client_id=%s&client_secret=%s&code=%s&state=%s&grant_type=authorization_code&redirect_uri=%s",K_CLIENT_ID,K_CLI_SECRET,code,state,K_redirectURI);
			
			URL tokenURL = new URL("https://kauth.kakao.com/oauth/token");
			HttpURLConnection con = (HttpURLConnection)tokenURL.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			con.setRequestProperty("Content-Length", Integer.toString(tokenParam.length()));
			con.setDoOutput(true);
			con.setDoInput(true);
			
			try (DataOutputStream dos = new DataOutputStream(con.getOutputStream())) {
				dos.writeBytes(tokenParam);
	        }
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String inputLine;
			
			while ((inputLine=br.readLine()) != null) {
				sb.append(inputLine);
			}
	
			String token = sb.toString();
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(token);

			String tempProfileURL =  "https://kapi.kakao.com/v2/user/me";
			String headerProfileURL = "Bearer " + object.get("access_token");
			
			URL profileURL = new URL(tempProfileURL);
			HttpURLConnection httpURL = (HttpURLConnection) profileURL.openConnection();
			
			httpURL.setRequestProperty("Authorization", headerProfileURL);
			httpURL.setRequestProperty("aceept", "application/json");
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(httpURL.getInputStream(),"UTF-8"));
			
			StringBuilder sb2 = new StringBuilder();
			String inputLine2;
			
			while ((inputLine2=br2.readLine()) != null) {
				sb2.append(inputLine2);

			}
			
			String profileData = sb2.toString();
			JSONObject object2 = (JSONObject) parser.parse(profileData);
			
			System.out.println(object2.get("id")+"여기");
			
			Member member = new Member();
			member.setMemberId(String.valueOf(object2.get("id")));
			member.setLoginMethod("KAKAO");
			
			session.setAttribute("login_member_id", member.getMemberId());
			session.setAttribute("login_member", member);
			
			if(mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}
			
			return "redirect:/course/select";
			
			
//			String encodeToken = ((String)object.get("id_token")).replace("\n","").replace("-", "+").replace("_", "/");	
//			byte[] a = Base64.getMimeDecoder().decode(encodeToken);
//			
//			
//			System.out.println(new String(a, StandardCharsets.UTF_8));
		
			
		}
		
		return  "redirect:/main";
	}
}