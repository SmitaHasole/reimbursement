package com.persistence;

/**
 * created by Smita Hasole on 02-05-2018
 */
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter

public class ResponseBean<T> implements Serializable {

    private String message;

    private String status;

    private transient T data;

    public ResponseBean() {
    }

    public ResponseBean(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
