package org.kanji.course.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.kanji.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Course {
	
	@Id
	private int course_id;
	
	@OneToOne(targetEntity = Member.class)
	private String member_id;
	
	private int course_period;
	
}
