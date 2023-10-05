package com.bootcamp.firstcheckout.domains.dtos;

import com.bootcamp.firstcheckout.domains.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Getter
@Setter
public class BaseEntityDTO {
    private UUID id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;
    private UUID updatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;
    private UUID createdBy;
    private Status status;
}
