package org.kanji.member.service;

import java.util.Optional;

import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private MemberRepository mRepo;
	
	//멤버 정보 가져오기 (입력 멤버)
	@Override
	public Optional<Member> getMember(Member member) {
		
		return mRepo.findById(member.getMemberId());
		
	}
	
	
	//멤버 정보 저장하기 (입력 멤버)
	@Override
	public void joinMember(Member member) {
		
		mRepo.save(member);
		
	}
	
}
