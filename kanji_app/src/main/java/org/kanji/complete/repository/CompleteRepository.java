package org.kanji.complete.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.kanji.complete.entity.Complete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompleteRepository extends JpaRepository<Complete, Integer> {
	@Query(value = "select * from complete where member_id = :member_id",nativeQuery = true)
	Optional<List<Complete>> findComplete(@Param("member_id")String member_id);
	
	@Modifying
	@Transactional
	@Query(value = "insert into complete (complete_passed,course_id,member_id) values (:#{#complete.completePassed},:#{#complete.course},:#{#complete.member})" ,nativeQuery = true)
	void saveComplete(@Param("complete")Complete comple);
	
	@Query(value = "select * from complete where member_id = :member_id and complete_passed = :complete_passed",nativeQuery = true)
	Optional<Complete> findCompleteOne(@Param("member_id")String member_id,@Param("complete_passed")int complete_passed);
	
	@Modifying
	@Transactional
	@Query(value = "update complete set complete_cycle = complete_cycle + 1,complete_date = (current_date) where (member_id = :member_id and complete_passed = :complete_passed)" ,nativeQuery = true)
	void updateComplete(@Param("member_id")String member_id,@Param("complete_passed")int complete_passed);
	
	@Modifying
	@Transactional
	@Query(value = "delete from complete where member_id = :member_id",nativeQuery = true)
	void deleteComplete(@Param("member_id")String member_id);
}
