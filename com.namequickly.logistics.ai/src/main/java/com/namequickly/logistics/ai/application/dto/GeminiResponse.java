package com.namequickly.logistics.ai.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {

    List<Content> contents;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        List<Part> parts;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part {
        Text text;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text {
        String text;
    }
}
