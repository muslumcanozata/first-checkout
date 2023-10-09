package com.bootcamp.firstcheckout.domains.dtos;

import com.bootcamp.firstcheckout.domains.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Getter
@Setter
public class BaseEntityDTO {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;
    private String updatedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;
    private String createdBy;
    private Status status;
}
