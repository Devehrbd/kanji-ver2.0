package org.kanji.social.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class SocialAccessTokenURL {
	
	private String N_CLIENT_ID = "";
	private String N_CLI_SECRET = "";
	private String G_CLIENT_ID = "";
	private String G_CLI_SECRET = "";
	private String K_CLIENT_ID = "";
	private String K_CLI_SECRET = "";
		
	public JSONObject getAccessTokenJSONObject(String social, String code, String state) throws Exception {
		
		Map<String, String> loginParam = getAccessTokenParam(social);
		String redirectURI = loginParam.get("redirect_uri");
		String tokenParam;
		
		if(social.equals("NAVER")) {
			 tokenParam = String.format(loginParam.get("param_url"),loginParam.get("client_id"),loginParam.get("client_secret"), code , state);
		}else {
			 tokenParam = String.format(loginParam.get("param_url"),loginParam.get("client_id"),loginParam.get("client_secret"), code , state , redirectURI );
		}
		
		URL tokenURL = new URL(loginParam.get("basic_url"));
		
		HttpURLConnection con = (HttpURLConnection)tokenURL.openConnection();
		
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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

		String tokenJSONString = sb.toString();

		JSONParser parser = new JSONParser();
		JSONObject tokenJSON = (JSONObject) parser.parse(tokenJSONString);
		
		return tokenJSON;
		
	}
	
	private Map<String,String> getAccessTokenParam(String social) throws UnsupportedEncodingException{
		Map<String, String> loginParam = new HashMap<>();
		switch (social) {
		case "GOOGLE" :
			
			loginParam.put("redirect_uri", URLEncoder.encode("http://localhost:8080/social/google", "UTF-8"));
			loginParam.put("param_url", "grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&state=%s&redirect_uri=%s");
			loginParam.put("basic_url", "https://oauth2.googleapis.com/token");
			loginParam.put("client_id", G_CLIENT_ID);
			loginParam.put("client_secret", G_CLI_SECRET);	
			
			break;
			
		case "KAKAO" :	
			
			loginParam.put("redirect_uri", URLEncoder.encode("http://localhost:8080/social/kakao", "UTF-8"));
			loginParam.put("basic_url", "https://kauth.kakao.com/oauth/token");
			loginParam.put("param_url", "grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&state=%s&redirect_uri=%s");
			loginParam.put("client_id", K_CLIENT_ID);
			loginParam.put("client_secret", K_CLI_SECRET);	
			
			break;
			
		default:
		
			loginParam.put("basic_url", "https://nid.naver.com/oauth2.0/token");
			loginParam.put("param_url", "grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&state=%s");
			loginParam.put("client_id", N_CLIENT_ID);
			loginParam.put("client_secret", N_CLI_SECRET);
			
			break;
		}
		return loginParam;
	}

}
