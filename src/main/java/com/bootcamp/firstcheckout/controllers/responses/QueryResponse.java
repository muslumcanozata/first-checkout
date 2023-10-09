package com.bootcamp.firstcheckout.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResponse<T> extends BaseResponse {
    private T message;

    public QueryResponse(Boolean result, T message) {
        super(result);
        this.message = message;
    }
}
