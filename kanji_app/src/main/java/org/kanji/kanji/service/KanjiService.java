package org.kanji.kanji.service;

import java.util.List;

import org.kanji.kanji.entity.Kanji;

public interface KanjiService {
	public List<Kanji> readListKanji(int kanji_index,int sep_kanji);
	public List<String> readListCharacter(int sep_kanji);
	public List<String> readListSoundMean(int sep_kanji);
	public List<Kanji> readFavoritesKanjiList(List<Integer> kanji_id);
}
