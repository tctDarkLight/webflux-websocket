package com.example.websocketwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {
    int code;
    String errorMessage;
    T result;
}
