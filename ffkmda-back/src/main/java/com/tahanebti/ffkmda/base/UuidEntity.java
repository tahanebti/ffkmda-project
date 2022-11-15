package com.tahanebti.ffkmda.base;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class UuidEntity extends BaseEntity<UUID> {

	@Id private UUID id;
	
	@Column
	 private Boolean isDeleted = Boolean.FALSE;
	
	@Version
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long version;
	 
	 @CreationTimestamp
	 private Date createdAt;

	 @UpdateTimestamp
	 private Date updatedAt;

	public UuidEntity() {
		this.id = UUID.randomUUID();
	}


	
}
