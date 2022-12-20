package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kanji.complete.entity.Complete;
import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/complete")
@AllArgsConstructor
public class CompleteController {
	
	private CourseServiceImpl cService;
	private CompleteServiceImpl cpService;
	
	@PostMapping("/regist")
	public String regist(@Param("complete_passed") int complete_passed, Authentication auth) {
		
		if (auth.getName() == null) {
			
			return "redirect:/member/loginPage";
			
		}
		
		Member member = new Member();
		member.setMemberId(auth.getName());
				
		Optional<Complete> comple = cpService.selectCompleteOne(auth.getName(), complete_passed);
		
		Optional<Course> course = cService.readCourse(auth.getName());
		
		Complete complete = new Complete();	
		complete.setCourse(course.get());
		complete.setMember(member);
		
		if(comple.isEmpty()) {
			complete.setCompletePassed(complete_passed);
			cpService.registComplete(complete);
		}else{
			cpService.updateComplete(member.getMemberId(),complete_passed);	
		}
		
		
		return "redirect:/kanji/listSelect2";
	}
	
	
	
}



