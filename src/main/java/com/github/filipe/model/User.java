package com.github.filipe.model;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name="user.findAll", query="SELECT name, email FROM User")
public class User implements Serializable {

	private static final long serialVersionUID = -8439381633589353151L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private LocalDate registeredIn;
	
	@Column
	private Boolean active;
	
	@ManyToMany
	@JoinTable(name="users_profiles", joinColumns=@JoinColumn(name="user"), inverseJoinColumns=@JoinColumn(name="profile"))
	private Set<Profile> profiles;

	public User() {}

	public User(String name, String email, String password, LocalDate registeredIn, Boolean active,
			Set<Profile> profiles) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.registeredIn = registeredIn;
		this.active = active;
		this.profiles = profiles;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getRegisteredIn() {
		return registeredIn;
	}

	public void setRegisteredIn(LocalDate registeredIn) {
		this.registeredIn = registeredIn;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}	

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
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
