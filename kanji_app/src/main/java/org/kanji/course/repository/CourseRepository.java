package org.kanji.course.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.kanji.course.entity.Course;
import org.kanji.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer>{
	
	@Query(value = "select * from course where member_id = :member_id",nativeQuery = true)
	Optional<Course> existCourse(@Param("member_id")String member_id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from course where member_id = :member_id",nativeQuery = true)
	void deleteCourse(@Param("member_id")String member_id);
}
