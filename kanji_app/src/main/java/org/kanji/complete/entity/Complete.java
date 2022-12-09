package org.kanji.complete.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.kanji.course.entity.Course;
import org.kanji.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complete {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complete_id")
	private int completeId;
	
	@ManyToOne
	@JoinColumn(name ="member_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name ="course_id")
	private Course course;
	
	@Column(name = "completePassed", nullable = false)
	private int completePassed;
	
	@Column(name = "complete_date", columnDefinition="DATE NOT NULL DEFAULT (CURRENT_DATE)")
	private Date completeDate;
	
	@Column(name = "complete_cycle", nullable = false)
	@ColumnDefault("0")
	private int completeCycle;
	
}

