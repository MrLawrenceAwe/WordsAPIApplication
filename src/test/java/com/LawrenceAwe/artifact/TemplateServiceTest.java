package com.LawrenceAwe.artifact;

import com.hubspot.jinjava.Jinjava;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TemplateServiceTest {
    private Jinjava mockJinjava;
    private TemplateService.ResourceLoader mockResourceLoader;
    private TemplateService templateService;

    @BeforeEach
    public void setup() {
        mockJinjava = Mockito.mock(Jinjava.class);
        mockResourceLoader = Mockito.mock(TemplateService.ResourceLoader.class);
        templateService = new TemplateService(mockJinjava, mockResourceLoader);
    }

    @Test
     void testRenderTemplate() throws Exception {
        // Given
        String path = "templatePath";
        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("key", "value");

        String loadedTemplate = "Hello, {{ key }}!";
        String expectedRenderedTemplate = "Hello, value!";

        when(mockResourceLoader.load(path)).thenReturn(loadedTemplate);
        when(mockJinjava.render(loadedTemplate, contextMap)).thenReturn(expectedRenderedTemplate);

        // When
        String result = templateService.renderTemplate(path, contextMap);

        // Then
        assertEquals(expectedRenderedTemplate, result);
    }

    @Test
     void testRenderTemplateWithException() throws Exception {
        // Given
        String path = "templatePath";
        Map<String, Object> contextMap = new HashMap<>();
        when(mockResourceLoader.load(path)).thenThrow(new RuntimeException("Loading error"));

        // When
        try {
            templateService.renderTemplate(path, contextMap);
        } catch (RuntimeException e) { // Then
            assertEquals("Loading error", e.getMessage());
        }
    }
}
