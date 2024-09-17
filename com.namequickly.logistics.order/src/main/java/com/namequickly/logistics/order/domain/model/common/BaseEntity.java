package com.namequickly.logistics.order.domain.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;

    @Column(updatable = false)
    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    protected String updatedBy;

    protected LocalDateTime deletedAt;

    protected String deletedBy;

    public void deleteEntity(String userName) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userName;
    }

}