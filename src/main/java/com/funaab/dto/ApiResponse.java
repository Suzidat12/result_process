package com.funaab.dto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ApiResponse {

    public static <T> ResponseEntity<ResponseDto<T>> ok(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        responseDto.setStatusCode("");
        responseDto.setStatusMessage("");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
