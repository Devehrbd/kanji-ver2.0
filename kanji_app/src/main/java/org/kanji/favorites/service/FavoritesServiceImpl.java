package org.kanji.favorites.service;

import java.util.List;
import java.util.Optional;

import org.kanji.favorites.entity.Favorites;
import org.kanji.favorites.repository.FavoritesRepository;
import org.kanji.kanji.entity.Kanji;
import org.kanji.kanji.repository.KanjiRepository;
import org.kanji.member.entity.Member;
import org.kanji.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
	
	
	private FavoritesRepository fRepo;
	
		
	@Override
	public void registFavorites(Favorites fav) {
			
		fRepo.save(fav);
			
	}
	
	@Override
	public Optional<Favorites> readFavorites(Member member, Kanji kanji) {
		
		
		return fRepo.readFavorites(member.getMemberId(), kanji.getKanjiId());
		
	}
	
	@Override
	public Optional<List<Favorites>> readFavoritesList(Member member) {
		// TODO Auto-generated method stub
		return fRepo.readFavoritesList(member.getMemberId());
	}
	
	@Override
	public void deleteFavorites(Member member, Kanji kanji) {
		
		fRepo.deleteFavorites(member.getMemberId(), kanji.getKanjiId());
		
	}
}
