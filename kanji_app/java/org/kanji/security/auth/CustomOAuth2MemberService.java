package org.kanji.security.auth;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.kanji.member.service.MemberService;
import org.kanji.security.domain.GoogleUserInfo;
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
	private final HttpSession session;
	private final MemberService mService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService delegate = new DefaultOAuth2UserService();
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
		}
		
		Optional<Member> member = mRepo.findByMemberId(oAuth2User.getAttribute("sub"));
		if(member.isEmpty()) {
			
			System.out.println("데이터베이스 저장해");
			
		} else {
			
			System.out.println("데이터베이스 이미 저장되었다.");
			
		}
	
	        
		return new PrincipalDetails(member.get(), oAuth2User.getAttributes());
	}
	
}
