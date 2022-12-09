package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.complete.entity.Complete;
import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.member.entity.Member;
import org.springframework.data.repository.query.Param;
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
	public String regist(@Param("complete_passed") int complete_passed, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Member login_member = (Member) session.getAttribute("login_member");
		Optional<Complete> comple = cpService.selectCompleteOne(login_member.getMemberId(), complete_passed);
		Course course = cService.readCourse(login_member.getMemberId()).get();
		
		Complete complete = new Complete();	
		complete.setCourse(course);
		complete.setMember(login_member);
		
		if(comple.isEmpty()) {
			complete.setCompletePassed(complete_passed);
			cpService.registComplete(complete);
		}else{
			cpService.updateComplete(login_member.getMemberId(),complete_passed);	
		}
		

		return "/main";
	}
	
	
	
}



