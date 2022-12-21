package org.kanji.favorites.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.kanji.favorites.entity.Favorites;
import org.kanji.kanji.entity.Kanji;
import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class FavoritesRepositoryTest {
	
	@Autowired
	private FavoritesRepository fRepo;
	@Autowired
	private MemberRepository mRepo;
	
	// 저장한 한자 조회
	@Test
	void readFavoritesOne() {
		List<Member>memberList = mRepo.findAll();
		int kanji_id = (int) (Math.random() * 2136);
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		Favorites fav = new Favorites();
		fav.setKanji(kanji);
		fav.setMember(memberList.get(0));
		
		fRepo.save(fav);
		assertThat(fRepo.readFavorites(fav.getMember().getMemberId(), fav.getKanji().getKanjiId())).isPresent();
		fRepo.delete(fav);
	}
	
	// Member값이 비어있을때 조회
	@Test
	void readFavorites_memberEmpty() {
		List<Member>memberList = mRepo.findAll();
		int kanji_id = (int) (Math.random() * 2136);
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		Favorites fav = new Favorites();
		fav.setKanji(kanji);
		fav.setMember(memberList.get(0));
		
		fRepo.save(fav);
		assertThat(fRepo.readFavorites(new Member().getMemberId(), fav.getKanji().getKanjiId())).isEmpty();
		fRepo.delete(fav);
	}
	
	// Kanji_id Empty
	@Test
	void readFavorites_kanjiIdEmpty() {
		List<Member>memberList = mRepo.findAll();
		int kanji_id = (int) (Math.random() * 2136);
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		Favorites fav = new Favorites();
		fav.setKanji(kanji);
		fav.setMember(memberList.get(0));
		
		fRepo.save(fav);
		assertThat(fRepo.readFavorites(memberList.get(0).getMemberId(), new Kanji().getKanjiId())).isEmpty();
		fRepo.delete(fav);
	}
	
	// favoritesList read
	@Test
	void readFavoritesList() {
		List<Member>memberList = mRepo.findAll();
		int kanji_id = (int) (Math.random() * 2136);
		
		Kanji kanji = new Kanji();
		kanji.setKanjiId(kanji_id);
		
		Favorites fav = new Favorites();
		fav.setKanji(kanji);
		fav.setMember(memberList.get(0));
		
		fRepo.save(fav);
		kanji.setKanjiId((int) (Math.random() * 2136));
		fav.setKanji(kanji);
		fRepo.save(fav);
		kanji.setKanjiId((int) (Math.random() * 2136));
		fav.setKanji(kanji);
		fRepo.save(fav);
		
		assertThat(fRepo.readFavoritesList(memberList.get(0).getMemberId())).isPresent();
		fRepo.deleteFavoritesAll(fav.getMember().getMemberId());
	}
	
	// delete의 경우는 column이 존재하지않아도 삭제가 가능해서 Test코드 작성 고민

}
