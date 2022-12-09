package org.kanji.complete.service;

import java.util.List;
import java.util.Optional;

import org.kanji.complete.entity.Complete;
import org.kanji.complete.repository.CompleteRepository;
import org.kanji.member.entity.Member;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompleteServiceImpl implements CompleteService {
	
	private CompleteRepository cpRepo;
	
	
	@Override
	public Optional<List<Complete>> selectCompleteAll(String member_id) {
		
		return cpRepo.findComplete(member_id);
		
	}
	
	@Override
	public void registComplete(Complete complete) {
		
		cpRepo.saveComplete(complete);
	}
	
	@Override
	public Optional<Complete> selectCompleteOne(String member_id, int complete_passed) {
		
		return cpRepo.findCompleteOne(member_id, complete_passed);
	}
	
	@Override
	public void updateComplete(String member_id, int complete_passed) {
		
		cpRepo.updateComplete(member_id, complete_passed);
	}
	
	@Override
	public void deleteComplete(Member member) {

		cpRepo.deleteComplete(member.getMemberId());
		
	}
}
