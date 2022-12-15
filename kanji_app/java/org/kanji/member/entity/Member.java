package org.kanji.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.kanji.security.domain.Role;

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

	@Column(unique = true, name="member_email")
	private String memberEmail;
	
	@Column(name="member_name")
	private String memberName;

	@Enumerated(EnumType.STRING)
    private Role role;
	
	public String getRoleKey(){
        return this.role.getKey();
    }

}