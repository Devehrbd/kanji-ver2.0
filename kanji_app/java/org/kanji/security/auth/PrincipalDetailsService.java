//package org.kanji.security.auth;
//
//import java.util.Optional;
//
//import org.kanji.member.entity.Member;
//import org.kanji.member.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PrincipalDetailsService implements UserDetailsService {
//	
//	@Autowired
//	private MemberRepository mRepo;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Member member = mRepo.findByMemberId(username).get();
//		if(member != null) {
//			return new PrincipalDetails(member);
//		}
//		
//		return null;
//	}
//
//}
