package org.kanji.complete.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.kanji.course.entity.Course;
import org.kanji.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
public class Complete {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int complete_id;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity = Member.class)
	private String member_id;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity = Course.class)
	private int course_id;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity = Course.class)
	private int course_passed;

	private Date complete_date;
	
}
