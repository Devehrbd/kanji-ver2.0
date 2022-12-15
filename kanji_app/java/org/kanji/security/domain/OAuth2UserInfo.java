package org.kanji.security.domain;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
}
