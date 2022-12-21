package org.kanji.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@SpringBootTest
class MemberServiceImplTest {

	@Autowired
	private MemberServiceImpl mService;
	@Autowired
	private MemberRepository mRepo;
	
	// 매개변수로 받은 Member클래스의 내부값이 존재하지않을 경우
	@Test
	void getMemberParameterMemberClassisEmpty() {
		Member member = new Member();
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()->{
			mService.getMember(member);
		});
	}
	
	// Member클래스 내부값이 존재할 경우 정상작동
	@Test
	void getMemberParameterPerfect() {
		List<Member> memberList = mRepo.findAll();
		if( memberList.get(0) != null ) {
			assertThat(mService.getMember(memberList.get(0))).isPresent();
		}
	}
	
	// Member클래스 내부값이 존재할 경우 정상작동
	@Test
	void getMemberParameterIncorrect() {
		Member member = new Member();
		member.setMemberId("gg");
		assertThat(mService.getMember(member)).isEmpty();
	}
}
