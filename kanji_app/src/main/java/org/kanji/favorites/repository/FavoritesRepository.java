package org.kanji.favorites.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.kanji.complete.entity.Complete;
import org.kanji.favorites.entity.Favorites;
import org.kanji.kanji.entity.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
	@Query(value = "select * from favorites where member_id = :member_id and kanji_id = :kanji_id",nativeQuery = true)
	Optional<Favorites> readFavorites(@Param("member_id")String member_id,@Param("kanji_id")int kanji_id);
	
	@Query(value = "select * from favorites where member_id = :member_id",nativeQuery = true)
	Optional<List<Favorites>> readFavoritesList(@Param("member_id")String member_id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from favorites where member_id = :member_id and kanji_id = :kanji_id",nativeQuery = true)
	void deleteFavorites(@Param("member_id")String member_id,@Param("kanji_id")int kanji_id);

}
