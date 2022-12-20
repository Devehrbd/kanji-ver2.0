package org.kanji.security.auth;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.kanji.member.service.MemberService;
import org.kanji.security.domain.GoogleUserInfo;
import org.kanji.security.domain.KakaoUserInfo;
import org.kanji.security.domain.NaverUserInfo;
import org.kanji.security.domain.OAuth2UserInfo;
import org.kanji.security.domain.Role;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final MemberRepository mRepo;
	private final MemberService mService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate= new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		// 여기는 과연 어떻게 돌아가는지 확인하기 좋을듯
		System.out.println("userRequest = " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest = " + userRequest.getClientRegistration());
        System.out.println("oAuth2User = " + userRequest.getAdditionalParameters());
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        System.out.println("oAuth2User.getAuthorities() = " + oAuth2User.getAuthorities());
        System.out.println("oAuth2User.getName() = " + oAuth2User.getName());
		
		OAuth2UserInfo oAuth2UserInfo = null;
		
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		}
		
		
		String sumProvider = oAuth2UserInfo.getProvider()+"_"+ String.valueOf(oAuth2UserInfo.getProviderId());   
		
		Optional<Member> memberOP = mRepo.findByMemberId(sumProvider);
		
		Member member = new Member();
		
		if(memberOP.isEmpty()) {
			
			member.setMemberId(sumProvider);
			member.setRole(Role.USER);
			mRepo.save(member);
			
		} else {
			
			member = memberOP.get();
			
		}
  
		return new PrincipalDetails(member, oAuth2User.getAttributes());
	}
	
}
