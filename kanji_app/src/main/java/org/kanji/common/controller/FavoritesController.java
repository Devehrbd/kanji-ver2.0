package org.kanji.common.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.kanji.favorites.entity.Favorites;
import org.kanji.favorites.service.FavoritesServiceImpl;
import org.kanji.kanji.entity.Kanji;
import org.kanji.member.entity.Member;
import org.kanji.security.auth.PrincipalDetails;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	public String regist(@Param("kanji_id") int kanji_id, @AuthenticationPrincipal PrincipalDetails prin) {
		
		if (prin == null) {
			
			return "redirect:/member/loginPage";
			
		}	
				
		Optional<Favorites> fav = Optional.empty();
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		fav = fService.readFavorites(prin.getMember(), kanji);
		
		Favorites fav2 = new Favorites();
		
		if(fav.isEmpty()) {
			
			fav2.setMember(prin.getMember());
			fav2.setKanji(kanji);
			fService.registFavorites(fav2);	
			
		}	

		
		return "/main";
	}
	
	@PostMapping("delete")
	public String delete(@Param("kanji_id") int kanji_id, @AuthenticationPrincipal PrincipalDetails prin) {

		if (prin == null) {
			
			return "redirect:/member/loginPage";
			
		}
			
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		fService.deleteFavorites(prin.getMember(), kanji);
		
		return "/main";
	}

}



