package com.github.filipe.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "roles")
@NamedEntityGraph(name="role.graph",attributeNodes=@NamedAttributeNode("profiles"))
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Role implements Serializable {

	private static final long serialVersionUID = -4426951619843181851L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String description;

	@Column
	private Boolean active;

	@ManyToMany(mappedBy = "roles")
	private Set<Profile> profiles;

	public Role() {
	}
	
	public Role(Long id, String description, Boolean active) {
		super();
		this.id = id;
		this.description = description;
		this.active = active;
	}
	
	public Role(String description, Boolean active) {
		super();
		this.description = description;
		this.active = active;
	}

	public Role(String description, Boolean active, Set<Profile> profiles) {
		super();
		this.description = description;
		this.active = active;
		this.profiles = profiles;
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
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}

}
