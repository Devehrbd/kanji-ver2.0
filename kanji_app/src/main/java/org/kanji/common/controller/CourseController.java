package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {
	
	private CourseServiceImpl cService;
	private CompleteServiceImpl cpService;
	
	@GetMapping("/select")
	public String select(HttpServletRequest request) {
	
	HttpSession session = request.getSession();
		
	String login_member_id = (String)session.getAttribute("login_member_id");
				
	Optional<Course> existCourse = cService.readCourse(login_member_id);
		
	if(existCourse.isPresent()) {
			
		return "redirect:/kanji/listSelect";
			
	}
	
	return "/course/select";
		
	}
	
	@PostMapping("/selectb")
	public String selectb(@Param("coursePeriod")int coursePeriod, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
				
		Course course = new Course();
		
		course.setCoursePeriod(coursePeriod);

		course.setMember((Member)session.getAttribute("login_member"));
		
		cService.registCourse(course);
		
		return "redirect:/kanji/listSelect";
	}
	
	@GetMapping("/reselection")
	public String reselection(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
				
		Member login_member= (Member)session.getAttribute("login_member");
		
		cpService.deleteComplete(login_member);
		
		cService.deleteCourse(login_member);
		
		return "redirect:/course/select";
	}
}
