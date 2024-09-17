package com.namequickly.logistics.ai.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastResponse {

    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private int resultCode;
    private String resultMsg;
    private String dataType;
    private LocalDate baseDate;
    private LocalDateTime baseTime;
    private int nx;
    private int ny;
    private String category;
    private int obsrValue;
}
