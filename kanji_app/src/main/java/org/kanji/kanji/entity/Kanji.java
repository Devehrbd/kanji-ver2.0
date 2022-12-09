package org.kanji.kanji.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kanji {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kanji_id")
	private int kanjiId;
	
	@Column(name = "kanji_character")
	private String kanjiCharacter;
	
	@Column(name = "kanji_soundMean")
	private String kanjiSoundMean;
	
	@Column(name = "jp_sound")
	private String jpSound;
	
	@Column(name = "jp_mean")
	private String jpMean;
	
	@Column(name = "jp_grade")
	private String jpGrade;
	
	
}	