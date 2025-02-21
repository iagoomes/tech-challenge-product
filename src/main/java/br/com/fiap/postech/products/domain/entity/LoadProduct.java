package br.com.fiap.postech.products.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoadProduct {
    public static final String NAME = "products.csv";
    private final byte[] binary;
    private final LocalDateTime timestamp;

    public LoadProduct(byte[] binary) {
        this.binary = binary;
        this.timestamp = LocalDateTime.now();
    }
}