package com.namequickly.logistics.ai.application.service;

import com.namequickly.logistics.ai.application.other.GeminiService;
import com.namequickly.logistics.ai.application.other.WeatherService;
import com.namequickly.logistics.ai.domain.model.AI;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final WeatherService weatherService;
    private final GeminiService geminiService;

    public AIService(WeatherService weatherService, GeminiService geminiService) {
        this.weatherService = weatherService;
        this.geminiService = geminiService;
    }

    // 메세지 생성

    // 메세지 발송

}
