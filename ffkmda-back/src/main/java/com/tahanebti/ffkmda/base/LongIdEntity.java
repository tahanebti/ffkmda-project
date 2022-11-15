package com.tahanebti.ffkmda.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class LongIdEntity extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 @Column
	 private Boolean isDeleted = Boolean.FALSE;
	
	 @Version
     @JsonProperty(access = JsonProperty.Access.READ_ONLY)
     private Long version;
	 
	 @CreationTimestamp
	 private Date createdAt;

	 @UpdateTimestamp
	 private Date updatedAt;

		 
}
