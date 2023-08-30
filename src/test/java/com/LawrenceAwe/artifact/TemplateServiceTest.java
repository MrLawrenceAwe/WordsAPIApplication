package com.LawrenceAwe.artifact;

import com.hubspot.jinjava.Jinjava;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TemplateServiceTest {

    private Jinjava mockJinjava;
    private TemplateService.ResourceLoader mockResourceLoader;
    private TemplateService templateService;

    @BeforeEach
    void setup() {
        mockJinjava = Mockito.mock(Jinjava.class);
        mockResourceLoader = Mockito.mock(TemplateService.ResourceLoader.class);
        templateService = new TemplateService(mockJinjava, mockResourceLoader);
    }

    @Test
    void testRenderTemplate() throws Exception {
        String path = "templatePath";
        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("key", "value");

        String loadedTemplate = "Hello, {{ key }}!";
        String expectedRenderedTemplate = "Hello, value!";

        Mockito.when(mockResourceLoader.load(path)).thenReturn(loadedTemplate);
        Mockito.when(mockJinjava.render(loadedTemplate, contextMap)).thenReturn(expectedRenderedTemplate);

        String result = templateService.renderTemplate(path, contextMap);

        assertEquals(expectedRenderedTemplate, result);
    }

    @Test
    void testRenderTemplate_IOException() throws Exception {
        String path = "templatePath";
        Map<String, Object> contextMap = new HashMap<>();

        Mockito.when(mockResourceLoader.load(path)).thenThrow(new IOException("IO error"));

        assertThrows(IOException.class, () -> templateService.renderTemplate(path, contextMap));
    }

    @Test
    void testResourceLoader_NullPointerException() throws Exception {
        TemplateService.ResourceLoader loader = new TemplateService.ResourceLoader();

        assertThrows(IOException.class, () -> loader.load("nonexistent/path.txt"));
    }

    @Test
    void testDefaultConstructor() {
        TemplateService service = new TemplateService();
        assertNotNull(service);
    }

    @Test
    void testDefaultResourceLoader_loadValidPath() throws Exception {
        TemplateService.ResourceLoader loader = new TemplateService.ResourceLoader();
        String templateContent = loader.load("templates/test.txt");

        assertNotNull(templateContent);
        assertFalse(templateContent.isEmpty());
    }

    @Test
    void testDefaultResourceLoader_loadInvalidPath() {
        TemplateService.ResourceLoader loader = new TemplateService.ResourceLoader();

        assertThrows(IOException.class, () -> loader.load("invalid/path.txt"));
    }

    @Test
    void testCustomResourceLoaderIntegration() throws Exception {
        TemplateService.ResourceLoader mockLoader = Mockito.mock(TemplateService.ResourceLoader.class);
        Mockito.when(mockLoader.load("testPath")).thenReturn("mocked template content");

        Jinjava jinjava = new Jinjava();
        TemplateService service = new TemplateService(jinjava, mockLoader);

        String rendered = service.renderTemplate("testPath", null);

        assertEquals("mocked template content", rendered);
    }
}
