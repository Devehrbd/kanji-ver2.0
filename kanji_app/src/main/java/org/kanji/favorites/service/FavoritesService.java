package org.kanji.favorites.service;

import java.util.List;
import java.util.Optional;

import org.kanji.favorites.entity.Favorites;
import org.kanji.kanji.entity.Kanji;
import org.kanji.member.entity.Member;

public interface FavoritesService {
	public void registFavorites(Favorites fav);
	public Optional<Favorites> readFavorites(Member member, Kanji kanji);
	public Optional<List<Favorites>> readFavoritesList(Member member);
	public void deleteFavorites(Member member, Kanji kanji);
}
