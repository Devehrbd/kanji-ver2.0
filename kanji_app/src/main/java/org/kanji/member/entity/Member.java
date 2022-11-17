package org.kanji.member.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Member {
	
	@Id
	private String member_id;
	
	private String member_name;
	
	private String member_pass;
	
	private String member_email;
	
	private String member_phone;
	
}
