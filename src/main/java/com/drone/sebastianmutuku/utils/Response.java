package com.drone.sebastianmutuku.utils;


import lombok.Data;

@Data
public class Response<P> {
    private String message;
    private P responseBody;
}
