package org.kanji.kanji.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kanji.kanji.entity.Kanji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
@SpringBootTest
class KanjiRepositoryTest {
	
	@Autowired
	private KanjiRepository kRepo;
	
	//2136개를 초과하여 데이터가 있을시, fail 
	@Test
	void readListKanji_OutOfRange() {
		assertThat(kRepo.readListKanji(2136, 50)).isEmpty();	
	}
	
	//2136개의 데이터베이스 kanji_id 변화가 있는지 확인.
	@Test
	void readListKanji_ListCountCorrect() {
		List<Kanji> listKanji = kRepo.readListKanji(2000, 10);
		if(listKanji.get(0).getKanjiId() == 2001) {
			if(listKanji.get(10-1).getKanjiId()==2001+10-1) {
				assertThat(true);
			}else {
				fail("숫자 부정확 (끝)");
			}
		}else {
			fail("숫자 부정확 (처음)");
		}
	}
	
	// 조회가 올바른지 확인
	@Test
	void findByKanjiIdIn_ListCorret(){
		List<Integer> kanji_id = new ArrayList<>();
		String a = null;
		for (int i = 0 ; i < 10; i ++) {
			kanji_id.add((int)(Math.random()*2136));		
		}
		Collections.sort(kanji_id);
		for (int i = 0; i < kanji_id.size(); i++) {
			assertThat(kRepo.findByKanjiIdIn(kanji_id).get(i).getKanjiId(),is(kanji_id.get(i)));
		}
	}
	
	// List에 Null 값이 들어갔을때 예외
	@Test
	void findByKanjiIdIn_ListNull(){
		List<Integer> kanji_id = null;
		assertThrows(InvalidDataAccessApiUsageException.class, ()->{
			kRepo.findByKanjiIdIn(kanji_id);
		});
	}
	
	// SoundMean, Character 같은경우는 , 향후 테스트 난이도를 올릴때 변화를 줄것이라 지금은 작성X . 매우단순.

}




//kanji_index sep_kanji 해서 몇번째 부터 ~ 몇개를 ? 이런느낌