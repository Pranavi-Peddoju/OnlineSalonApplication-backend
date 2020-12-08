package com.cg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Component
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Scope("prototype")
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5124311823439996694L;

	@Id
	@Column(name = "USER_ID")
	private long userId;
	@Column(name = "USER_NAME", unique = true, nullable = false)
	@NotEmpty(message = "User Name is a mandatory field. Please provide user Name")
	@Size(min = 5, max = 20, message = "User Name must be in between 5 to 20 characters")
	private String userName;
	@Column(name = "PASSWORD")
	private String password;

}
