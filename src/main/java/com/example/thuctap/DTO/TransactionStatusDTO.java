package com.example.thuctap.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionStatusDTO<T> implements Serializable {

        private String error;
        private T data;
        private int status;

}
