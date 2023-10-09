package com.bootcamp.firstcheckout.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandResponse extends BaseResponse {
    private String message;

    public CommandResponse(Boolean result, String message) {
        super(result);
        this.message = message;
    }
}
