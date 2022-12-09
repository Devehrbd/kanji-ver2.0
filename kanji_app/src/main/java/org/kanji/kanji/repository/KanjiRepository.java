package org.kanji.kanji.repository;

import java.util.List;
import java.util.Optional;

import org.kanji.complete.entity.Complete;
import org.kanji.kanji.entity.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KanjiRepository extends JpaRepository<Kanji, Integer> {

	@Query(value = "select * from kanji limit :kanji_index,:sep_kanji",nativeQuery = true)
	List<Kanji> readListKanji(@Param("kanji_index")int kanji_index,@Param("sep_kanji")int sep_kanji);
	
	@Query(value = "select kanji_character from kanji order by rand() limit :sep_kanji",nativeQuery = true)
	List<String> readListCharacter(@Param("sep_kanji")int sep_kanji);
	
	@Query(value = "select kanji_sound_mean from kanji order by rand() limit :sep_kanji",nativeQuery = true)
	List<String> readListSoundMean(@Param("sep_kanji")int sep_kanji);
	
	List<Kanji> findByKanjiIdIn(List<Integer> kanji_id);
}
