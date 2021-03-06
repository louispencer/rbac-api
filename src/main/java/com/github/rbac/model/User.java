package com.github.rbac.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "users")
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class User implements Serializable, ModelEntity {

	private static final long serialVersionUID = -8439381633589353151L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Column(updatable=false)
	@Temporal(TemporalType.DATE)
	private Date registeredIn;
	
	@Column
	private Boolean active;

	public User() {
		super();
	}

	public User(String name, String email, String password, Boolean active) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.active = active;
	}
	
	public User(Long id, String name, String email, Date registeredIn, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.registeredIn = registeredIn;
		this.active = active;
	}
	
	public User(String name, String email, Boolean active) {
		super();
		this.name = name;
		this.email = email;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@ApiModelProperty(hidden=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getRegisteredIn() {
		return registeredIn;
	}

	public void setRegisteredIn(Date registeredIn) {
		this.registeredIn = registeredIn;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
