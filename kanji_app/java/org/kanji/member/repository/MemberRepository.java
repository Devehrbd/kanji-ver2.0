package org.kanji.member.repository;

import java.util.Optional;

import org.kanji.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member>findByMemberId(String memberId);
}
