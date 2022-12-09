package org.kanji.common.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kanji.complete.entity.Complete;
import org.kanji.complete.service.CompleteServiceImpl;
import org.kanji.course.entity.Course;
import org.kanji.course.service.CourseServiceImpl;
import org.kanji.favorites.entity.Favorites;
import org.kanji.favorites.service.FavoritesServiceImpl;
import org.kanji.kanji.entity.Kanji;
import org.kanji.kanji.service.KanjiServiceImpl;
import org.kanji.member.entity.Member;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/kanji")
@AllArgsConstructor
public class KanjiController {
	
	private CourseServiceImpl cService;
	private CompleteServiceImpl cpService;
	private KanjiServiceImpl kService;
	private FavoritesServiceImpl fService;
	
	@GetMapping("listSelect")
	public void listSelect() {
	
	}

	@GetMapping("/listSelect2")
	public void listSelect2(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		
		session.getAttribute("login_member");
				
		
		String login_member_id = (String)session.getAttribute("login_member_id");
					
		Optional<Course> current_course = cService.readCourse(login_member_id);
		List<Integer> course_period = new ArrayList<>();
		List<String> course_message = new ArrayList<>();
		
	
		for (int i = 1; i<= current_course.get().getCoursePeriod(); i++) {
			
			course_period.add(i);
			course_message.add("테스트 미완료");	
			
		}
		
		Optional<List<Complete>> tempComplete = cpService.selectCompleteAll(login_member_id);
		
		if(tempComplete.isPresent()) {
			Calendar curDate = Calendar.getInstance();
			
			for(int i = 0; i < tempComplete.get().size(); i++) {
				Calendar comDate = Calendar.getInstance();
				Date date = tempComplete.get().get(i).getCompleteDate();
				comDate.setTime(date);
				
				
				switch(tempComplete.get().get(i).getCompleteCycle()) {
				
					case 0 : comDate.add(Calendar.DATE, 1);
							 if(curDate.after(comDate)) {
								 course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "복습기간도래");
							 }else {
								 course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "테스트 완료(복습기간미도래)");
							 }
							 break;
					case 1 : comDate.add(Calendar.DATE, 7);
							 if(curDate.after(comDate)) {
								 course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "복습기간도래");
							 }else {
								 course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "테스트 완료(복습기간미도래)");
							 }
							 break;
					
					case 2 : comDate.add(Calendar.DATE, 14);
					 		if(curDate.after(comDate)) {
					 			course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "복습기간도래");
					 		}else {
					 			course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "테스트 완료(복습기간미도래)");
					 		}
					 		break;
							
					default : comDate.add(Calendar.DATE, 30);
							if(curDate.after(comDate)) {
								course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "복습기간도래");
							}else {
								course_message.set(tempComplete.get().get(i).getCompletePassed()-1, "테스트 완료(복습기간미도래)");
							}
							
							break;	
							
				}
			}			
		}
		
		
		
		
		
		model.addAttribute("course_period", course_period);
		model.addAttribute("course_message", course_message);
	}
	
	@GetMapping("/list")
	public void list(@Param("course_index")int course_index,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		String login_member_id = (String)session.getAttribute("login_member_id");
		int current_course = cService.readCourse(login_member_id).get().getCoursePeriod();
		
		List<Kanji> kanji_list = new ArrayList<>();
	
		int unit_kanji = 2136 / current_course;
		int lack_kanji = 2136%(unit_kanji*current_course);
		int remain_kanji = (lack_kanji - (course_index-1)) % (unit_kanji * current_course);
		int kanji_index;
		
		
		if (remain_kanji > 0) {
			kanji_index = unit_kanji * (course_index-1) + lack_kanji - remain_kanji;
		}else {
			kanji_index = unit_kanji * (course_index-1) + lack_kanji;
		}
		int sep_kanji;
		
		if(remain_kanji > 0 ) {
			sep_kanji = unit_kanji +1;
		}else {
			sep_kanji = unit_kanji;
		}

		kanji_list = kService.readListKanji(kanji_index, sep_kanji);
		
		
		List<Integer> favorites_num = new ArrayList<>();
		Optional<List<Favorites>> fav = fService.readFavoritesList((Member)session.getAttribute("login_member"));
		
		if(fav.isPresent()) {
			
			for(int i = 0; i < fav.get().size(); i++) {
				
				if(fav.get().get(i).getKanji().getKanjiId() >= kanji_list.get(0).getKanjiId()
						&& fav.get().get(i).getKanji().getKanjiId() <= kanji_list.get(kanji_list.size()-1).getKanjiId()) {
					favorites_num.add(fav.get().get(i).getKanji().getKanjiId());
				}

			}

		}
		
		List<Integer> favorites_num2 = new ArrayList<>();
		
		for (int i = 0; i < kanji_list.size(); i++) {
			favorites_num2.add(null);
		}
		
		for (int i = 0; i < kanji_list.size(); i++) {
			
			for(int j = 0; j < favorites_num.size(); j++) {
				if(kanji_list.get(i).getKanjiId() == favorites_num.get(j)) {
					favorites_num2.set(i,favorites_num.get(j));
				}
			}
		}
		
		model.addAttribute("favorites_list",favorites_num2);
		model.addAttribute("kanji_list",kanji_list);
		model.addAttribute("course_index",course_index);

		
	}
	
	@GetMapping("/testSelect")
	public void testSelect(@Param("course_index")int course_index,Model model) {
	
		model.addAttribute("course_index",course_index);
		
	}
	
	@GetMapping("/test")
	public void test(@Param("type")String type,@Param("course_index")int course_index,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String login_member_id = (String)session.getAttribute("login_member_id");
		int current_course = cService.readCourse(login_member_id).get().getCoursePeriod();
		
		List<Kanji> kanji_list = new ArrayList<>();
	
		int unit_kanji = 2136 / current_course;
		int lack_kanji = 2136%(unit_kanji*current_course);
		int remain_kanji = (lack_kanji - (course_index-1)) % (unit_kanji * current_course);
		int kanji_index;
		
		
		if (remain_kanji > 0) {
			kanji_index = unit_kanji * (course_index-1) + lack_kanji - remain_kanji;
		}else {
			kanji_index = unit_kanji * (course_index-1) + lack_kanji;
		}
		int sep_kanji;
		
		if(remain_kanji > 0 ) {
			sep_kanji = unit_kanji +1;
		}else {
			sep_kanji = unit_kanji;
		}
		
		List<String> kokai1 = new ArrayList<>();
		List<String> kokai2 = new ArrayList<>();
		List<String> kokai3 = new ArrayList<>();
		
		
		if(type.equals("character")) {
			
			kokai1 = kService.readListCharacter(sep_kanji);
			kokai2 = kService.readListCharacter(sep_kanji);
			kokai3 = kService.readListCharacter(sep_kanji);
			
		}else{
			
			kokai1 = kService.readListSoundMean(sep_kanji);
			kokai2 = kService.readListSoundMean(sep_kanji);
			kokai3 = kService.readListSoundMean(sep_kanji);
			
		}
		
		
		
		kanji_list = kService.readListKanji(kanji_index, sep_kanji);
		Collections.shuffle(kanji_list);
	
		model.addAttribute("type",type);
		model.addAttribute("kanji_list",kanji_list);
		model.addAttribute("course_index",course_index);
		model.addAttribute("kokai1",kokai1);
		model.addAttribute("kokai2",kokai2);
		model.addAttribute("kokai3",kokai3);
	}
	
	@GetMapping("/favoritesList")
	public void favList(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		Member login_member = (Member)session.getAttribute("login_member");
		
		Optional<List<Favorites>> favorites_list = Optional.empty();
		favorites_list = fService.readFavoritesList(login_member);
		
		List<Integer> kanji_id_list = new ArrayList<>();
		
		if(favorites_list.isPresent()) {
			
			for(int i = 0; i < favorites_list.get().size(); i++) {
				
				kanji_id_list.add(favorites_list.get().get(i).getKanji().getKanjiId());
			}
			
		}
		
		List<Kanji> kanji_favorites_list = kService.readFavoritesKanjiList(kanji_id_list);
		
		model.addAttribute("kanji_list",kanji_favorites_list);		
		
	}
	
	@GetMapping("/favoritesTestSelect")
	public void testSelect(Model model) {
			
	}
	
	@GetMapping("/favoritesTest")
	public void favoritesTest(@Param("type")String type,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Member login_member = (Member)session.getAttribute("login_member");

		Optional<List<Favorites>> favorites_list = Optional.empty();
	
		favorites_list = fService.readFavoritesList(login_member);
		
		List<Integer> kanji_id_list = new ArrayList<>();
		
		if(favorites_list.isPresent()) {
			
			for(int i = 0; i < favorites_list.get().size(); i++) {
				
				kanji_id_list.add(favorites_list.get().get(i).getKanji().getKanjiId());
			}
			
		}
		
		List<Kanji> kanji_list = kService.readFavoritesKanjiList(kanji_id_list);
		
		List<String> kokai1 = new ArrayList<>();
		List<String> kokai2 = new ArrayList<>();
		List<String> kokai3 = new ArrayList<>();
		
		
		if(type.equals("character")) {
			
			kokai1 = kService.readListCharacter(kanji_list.size());
			kokai2 = kService.readListCharacter(kanji_list.size());
			kokai3 = kService.readListCharacter(kanji_list.size());
			
		}else{
			
			kokai1 = kService.readListSoundMean(kanji_list.size());
			kokai2 = kService.readListSoundMean(kanji_list.size());
			kokai3 = kService.readListSoundMean(kanji_list.size());
			
		}

		Collections.shuffle(kanji_list);
	
		model.addAttribute("type",type);
		model.addAttribute("kanji_list",kanji_list);
		model.addAttribute("kokai1",kokai1);
		model.addAttribute("kokai2",kokai2);
		model.addAttribute("kokai3",kokai3);
	}
}
