package com.palja.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.palja.common.auditor.CurrentUser;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "created_by", length = 10, updatable = false)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "updated_by", length = 10)
	private String updatedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted_by", length = 10)
	private String deletedBy;

	@PrePersist
	public void prePersist() {
		this.createdBy = CurrentUser.getLoginId();
		this.updatedBy = CurrentUser.getLoginId();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedBy = CurrentUser.getLoginId();
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = CurrentUser.getLoginId();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}

}
