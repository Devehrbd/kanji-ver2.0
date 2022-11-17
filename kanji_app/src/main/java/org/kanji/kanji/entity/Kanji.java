package org.kanji.kanji.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private int kanji_id;
	
	
	private String kanji_character;
	
	private String kanji_soundMean;
	
	private String jp_sound;
	
	private String jp_mean;
	
	
}	
