package com.bootcamp.firstcheckout.domains.models;

import com.bootcamp.firstcheckout.domains.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    private UUID id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private String updatedAt;
    @Column(name = "updated_by")
    private UUID updatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "created_by")
    private UUID createdBy;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private StatusEnum status;
}


