package org.kanji.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	
	@Id
	@Column(name = "member_id")
	private String memberId;
	
	@Column(name = "member_name")
	private String memberName;
	
	@Column(name = "member_pass")
	private String memberPass;
	
	@Column(name = "member_email")
	private String memberEmail;
	
	@Column(name = "member_phone")
	private String memberPhone;
	
}