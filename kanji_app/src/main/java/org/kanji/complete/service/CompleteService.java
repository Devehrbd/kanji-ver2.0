package org.kanji.complete.service;

import java.util.List;
import java.util.Optional;

import org.kanji.complete.entity.Complete;
import org.kanji.member.entity.Member;

public interface CompleteService {
	public Optional<List<Complete>> selectCompleteAll(String member_id); 
	public void registComplete(Complete complete);
	public Optional<Complete> selectCompleteOne(String member_id, int complete_passed);
	public void updateComplete(String member_id, int complete_passed);
	public void deleteComplete(Member member);
}
