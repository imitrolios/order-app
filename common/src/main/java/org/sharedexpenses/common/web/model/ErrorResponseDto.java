package org.sharedexpenses.common.web.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    private Integer status;

    private String error;

    private String message;

    private String path;

    private LocalDateTime timestamp;
}

