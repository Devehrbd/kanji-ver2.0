package org.kanji.social.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class SocialAPI {

	public JSONObject getProfileUsers(String social, String accessToken) throws Exception {
		
		Map<String, String> loginParam = getProfileParam(social);
				
		URL profileURL = new URL(loginParam.get("basic_url"));
		
		HttpURLConnection con = (HttpURLConnection)profileURL.openConnection();
		
		String profileURLHeader = "Bearer " + accessToken;

		con.setRequestProperty("Authorization", profileURLHeader);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

		StringBuilder sb = new StringBuilder();
		String inputLine;
		
		while ((inputLine=br.readLine()) != null) {
			sb.append(inputLine);
		}

		String profileJSONString = sb.toString();

		JSONParser parser = new JSONParser();
		JSONObject profileJSON = (JSONObject) parser.parse(profileJSONString);
		
		return profileJSON;
		
	}
	
	private Map<String,String> getProfileParam(String social) throws UnsupportedEncodingException{
		Map<String, String> loginParam = new HashMap<>();
		switch (social) {
		case "GOOGLE" :

			loginParam.put("basic_url", "https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
		
			break;
			
		case "KAKAO" :	

			loginParam.put("basic_url", "https://kapi.kakao.com/v2/user/me");
			
			break;
			
		default:

			loginParam.put("basic_url", "https://openapi.naver.com/v1/nid/me");
			
			break;
		}
		return loginParam;
	}

}
