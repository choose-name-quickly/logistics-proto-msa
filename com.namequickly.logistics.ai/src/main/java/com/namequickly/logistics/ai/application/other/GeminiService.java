package com.namequickly.logistics.ai.application.other;

import com.namequickly.logistics.ai.application.dto.GeminiRequest;
import com.namequickly.logistics.ai.application.dto.GeminiResponse;

public interface GeminiService {

    GeminiRequest createWeatherRequest(String info);
    GeminiRequest createDeliveryRequest(String info);
    GeminiResponse getGeminiData(GeminiRequest request);
}
