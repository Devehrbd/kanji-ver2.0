package org.kanji.tests;

import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.kanji.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Profile("test")
public class TestUserDetailService implements UserDetailsService {
	
	@Autowired
	private MemberRepository mRepo;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = mRepo.findAll().get(0);
		return new PrincipalDetails(member);
	}

	
	
}
