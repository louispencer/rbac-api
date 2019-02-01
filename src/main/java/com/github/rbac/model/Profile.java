package com.github.rbac.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "profiles")
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Profile implements Serializable, ModelEntity {

	private static final long serialVersionUID = 2719175420702043748L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String description;
	
	@Column
	private Boolean active;
	
	@ManyToMany
	@JoinTable(name="profiles_roles", joinColumns=@JoinColumn(name="profile"), inverseJoinColumns=@JoinColumn(name="role"))
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(name="users_profiles", joinColumns=@JoinColumn(name="profile"), inverseJoinColumns=@JoinColumn(name="user"))
	private Set<User> users;

	public Profile() {}
	
	public Profile(String description, Boolean active, Set<Role> roles) {
		super();
		this.description = description;
		this.active = active;
		this.roles = roles;
	}
	
	public Profile(Long id, String description, Boolean active) {
		super();
		this.id = id;
		this.description = description;
		this.active = active;
	}
	
	public Profile(String description, Boolean active) {
		super();
		this.description = description;
		this.active = active;
	}
	
	public Profile(Long id, String description, Boolean active, Set<Role> roles) {
		super();
		this.id = id;
		this.description = description;
		this.active = active;
		this.roles = roles;
	}
	
	public Profile(Long id, String description, Boolean active, Set<Role> roles, Set<User> users) {
		super();
		this.id = id;
		this.description = description;
		this.active = active;
		this.roles = roles;
		this.users = users;
	}
	
	public Profile(String description, Boolean active, Set<Role> roles, Set<User> users) {
		super();
		this.description = description;
		this.active = active;
		this.roles = roles;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		return Objects.equals(id, other.id);
	}

}
