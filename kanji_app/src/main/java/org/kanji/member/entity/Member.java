package org.kanji.member.entity;

import java.io.Serializable;

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
public class Member implements Serializable{
	
	@Id
	@Column(name = "member_id")
	private String memberId;
	
	@Enumerated(EnumType.STRING)
    private Role role;
	
	public String getRoleKey(){
        return this.role.getKey();
    }

}