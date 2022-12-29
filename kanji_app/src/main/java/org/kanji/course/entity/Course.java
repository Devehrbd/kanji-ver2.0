package org.kanji.course.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kanji.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	@Id
	@Column(name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;
	
	@ManyToOne
	@JoinColumn(name ="member_id")
	private Member member;
	
	@Column(name = "course_period")
	private int coursePeriod;
	
}