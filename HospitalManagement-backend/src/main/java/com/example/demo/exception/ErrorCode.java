package com.example.demo.exception;

import java.util.Date;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorCode {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}