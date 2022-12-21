package org.kanji.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kanji.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@SpringBootTest
class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository mRepo;
	
	// 매개변수 member_id가 존재하는 값일 때 정상 작동
	@Test
	void findByMemberIdParameterCorrect(){
		List<Member> memberList = mRepo.findAll();
		if(memberList.get(0).getMemberId() != null) {
			assertThat(mRepo.findById(memberList.get(0).getMemberId())).isPresent();
		}
	}
	
	// 매개 변수 member_id가 존재하지 않는 값일 때
	@Test
	void findByMemberIdParameterisNull(){
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			mRepo.findById(null);	
		});	
	}
	
	// 매개 변수 member_id가 맞지않는 값일때
	@Test
	void findByMemberIdParameterIncorrect(){
		assertThat(mRepo.findById("gg")).isEmpty();
	}

	
	
}
