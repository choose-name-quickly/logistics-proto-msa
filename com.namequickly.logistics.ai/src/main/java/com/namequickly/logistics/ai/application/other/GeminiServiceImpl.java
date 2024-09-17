package com.namequickly.logistics.ai.application.other;

import com.namequickly.logistics.ai.application.dto.GeminiRequest;
import com.namequickly.logistics.ai.application.dto.GeminiResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GeminiServiceImpl implements  GeminiService {

    private final RestTemplate restTemplate;

    public GeminiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 날씨 정보와 배송 정보를 나눠 요약
    // 날씨 정보 request 생성
    @Override
    public GeminiRequest createWeatherRequest(String weatherInfo) {

        // TODO: 요약정보를 가져오기.
        //  배송 정보와 날씨 정보를 요약해서 한꺼번에 보내기
        GeminiRequest.Text text = new GeminiRequest.Text("다음내용을 요약해줘" + "\n" + weatherInfo);
        GeminiRequest.Part part = new GeminiRequest.Part(text);
        GeminiRequest.Content content = new GeminiRequest.Content(Arrays.asList(part));
        GeminiRequest request = new GeminiRequest(Arrays.asList(content));

        return request;
    }


    @Override
    public GeminiRequest createDeliveryRequest(String deliveryInfo) {

        GeminiRequest.Text text = new GeminiRequest.Text("다음내용을 요약해줘" + "\n" + deliveryInfo);
        GeminiRequest.Part part = new GeminiRequest.Part(text);
        GeminiRequest.Content content = new GeminiRequest.Content(Arrays.asList(part));
        GeminiRequest request = new GeminiRequest(Arrays.asList(content));
        return null;
    }

    @Override
    public GeminiResponse getGeminiData(GeminiRequest request) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyBqj2tS76_EKHa6LkKoGTSskdtVyHkjKhg";

        // Hearder 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<GeminiRequest> geminiRequest = new HttpEntity<>(request, headers);

        ResponseEntity<GeminiResponse> response = restTemplate.exchange(url, HttpMethod.POST, geminiRequest, GeminiResponse.class);
        return response.getBody();
    }
}
