package com.namequickly.logistics.ai.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastRequest {


    private String serviceKey;
    private int pageNo;
    private int numOfRows;
    private String dateType;
    private LocalDate baseDate;
    private LocalDateTime baseTime;
    private int nx;
    private int ny;
}
