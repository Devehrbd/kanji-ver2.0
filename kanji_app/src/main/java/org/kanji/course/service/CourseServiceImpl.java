package org.kanji.course.service;

import java.util.Optional;

import org.kanji.course.entity.Course;
import org.kanji.course.repository.CourseRepository;
import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
	
	private CourseRepository cRepo;
	//코스 등록
	@Override
	public void registCourse(Course course) {
		cRepo.save(course);
	}
	
	//코스 확인
	@Override
	public Optional<Course> readCourse(String member_id) {
		
		return cRepo.existCourse(member_id);
	}
	
	@Override
	public void deleteCourse(Member member) {
		
		cRepo.deleteCourse(member.getMemberId());
	}
}
