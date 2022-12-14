package org.kanji.security.domain;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributes;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "kakao";
	}



}
