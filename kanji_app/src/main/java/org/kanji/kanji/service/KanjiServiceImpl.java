package org.kanji.kanji.service;

import java.util.List;

import org.kanji.kanji.entity.Kanji;
import org.kanji.kanji.repository.KanjiRepository;
import org.kanji.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KanjiServiceImpl implements KanjiService {
	
		private KanjiRepository kRepo;
		
		@Override
		public List<Kanji> readListKanji(int kanji_index, int sep_kanji) {
			return kRepo.readListKanji(kanji_index, sep_kanji);
		}
		
		@Override
		public List<String> readListCharacter(int sep_kanji) {
			return kRepo.readListCharacter(sep_kanji);
		}
		
		@Override
		public List<String> readListSoundMean(int sep_kanji) {
	
			return kRepo.readListSoundMean(sep_kanji);
		}
		
		@Override
		public List<Kanji> readFavoritesKanjiList(List<Integer> kanji_id) {
		
		return kRepo.findByKanjiIdIn(kanji_id);
		}
}
