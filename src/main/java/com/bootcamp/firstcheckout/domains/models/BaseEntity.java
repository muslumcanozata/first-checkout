package com.bootcamp.firstcheckout.domains.models;

import com.bootcamp.firstcheckout.domains.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id")
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}


