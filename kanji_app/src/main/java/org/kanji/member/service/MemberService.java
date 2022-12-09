package org.kanji.member.service;

import java.util.Optional;

import org.kanji.member.entity.Member;

public interface MemberService {
	public Optional<Member> getMember(Member member); 
	public void joinMember(Member member);
}
