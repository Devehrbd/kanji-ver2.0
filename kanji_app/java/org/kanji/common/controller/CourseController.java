package org.kanji.common.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.member.entity.Member;
import org.kanji.security.auth.PrincipalDetails;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	public String select(@AuthenticationPrincipal PrincipalDetails prin) {
		if (prin == null) {

			return "redirect:/member/loginPage";
			
		}
		
		Optional<Course> existCourse = cService.readCourse(prin.getUsername());
		
		if(existCourse.isPresent()) {
			
			return "redirect:/kanji/listSelect";
			
		}
	
		return "/course/select";
		
		}
	
	@PostMapping("/selectb")
	public String selectb(@Param("coursePeriod")int coursePeriod, @AuthenticationPrincipal PrincipalDetails prin) {
		
		Course course = new Course();
		
		course.setCoursePeriod(coursePeriod);
		
		course.setMember(prin.getMember());
		
		cService.registCourse(course);
		
		return "redirect:/kanji/listSelect";
		
	}
	
	@GetMapping("/reselection")
	public String reselection(@AuthenticationPrincipal PrincipalDetails prin) {
		
		if (prin == null) {
			
			return "redirect:/member/loginPage";
			
		}
						
		cpService.deleteComplete(prin.getMember());
		
		cService.deleteCourse(prin.getMember());
		
		return "redirect:/course/select";
	}
}
