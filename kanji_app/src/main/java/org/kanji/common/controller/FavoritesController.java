package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.complete.entity.Complete;
import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.favorites.entity.Favorites;
import org.kanji.favorites.service.FavoritesService;
import org.kanji.favorites.service.FavoritesServiceImpl;
import org.kanji.kanji.entity.Kanji;
import org.kanji.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/favorites")
@AllArgsConstructor
public class FavoritesController {

	private FavoritesServiceImpl fService;
	
	@PostMapping("/regist")
	public String regist(@Param("kanji_id") int kanji_id, HttpServletRequest request) {

		HttpSession session = request.getSession();
			
		Member login_member = (Member)session.getAttribute("login_member");
		
		Optional<Favorites> fav = Optional.empty();
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		fav = fService.readFavorites(login_member, kanji);
		
		Favorites fav2 = new Favorites();
		
		if(fav.isEmpty()) {
			
			fav2.setMember(login_member);
			fav2.setKanji(kanji);
			fService.registFavorites(fav2);	
			
		}	

		
		return "/main";
	}
	
	@PostMapping("delete")
	public String delete(@Param("kanji_id") int kanji_id, HttpServletRequest request) {

		HttpSession session = request.getSession();
			
		Member login_member = (Member)session.getAttribute("login_member");

		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		fService.deleteFavorites(login_member, kanji);
		
		return "/main";
	}

}



