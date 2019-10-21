package com.iktpreobuka.e_diary.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_diary.entities.UserEntity;
import com.iktpreobuka.e_diary.enumerations.ERole;
import com.iktpreobuka.e_diary.security.Views;

public class UserDTO {
	
	private Long id;
	
	@JsonView (Views.Admin.class)
	@NotNull(message = "Email must be provided!")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email is not valid!")
	private String username;
	
	
	@JsonView (Views.Admin.class)
	@NotNull(message = "Password must be provided!")
	private String password;
	
	@JsonView (Views.Admin.class)
	@NotNull(message = "Person must be provided!")
	private Long personID;
	
	@JsonView (Views.Admin.class)
	@NotNull(message = "Role must be provided!")
	private ERole role;

	
	public UserDTO(Long id, String username, String password, Long personID, ERole role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.personID = personID;
		this.role = role;
	}
	
	public UserDTO(UserEntity u) {
		super();
		this.id = u.getId();
		this.username = u.getUsername();
		this.password = u.getPassword();
		this.personID = u.getPerson().getId();
		this.role = u.getRole();
	}
	
	
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Long getPersonID() {
		return personID;
	}
	public ERole getRole() {
		return role;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPersonID(Long personID) {
		this.personID = personID;
	}
	public void setRole(ERole role) {
		this.role = role;
	}
	
	

}
