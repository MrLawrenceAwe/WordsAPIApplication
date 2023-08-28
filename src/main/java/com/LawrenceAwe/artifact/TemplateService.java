package com.LawrenceAwe.artifact;

import com.hubspot.jinjava.Jinjava;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.file.Files;
import java.util.Map;

public class TemplateService {
    private final Jinjava jinjava;
    private final ResourceLoader resourceLoader;

    public TemplateService() {
        this(new Jinjava(), new DefaultResourceLoader());
    }

    public TemplateService(Jinjava jinjava, ResourceLoader resourceLoader) {
        this.jinjava = jinjava;
        this.resourceLoader = resourceLoader;
    }

    public String renderTemplate(String templatePath, Map<String, Object> context) throws Exception {
        String template = resourceLoader.load(templatePath);
        return jinjava.render(template, context);
    }

    // Separate interface for resource loading for better testability
    public interface ResourceLoader {
        String load(String path) throws Exception;
    }

    // Default implementation using ClassPathResource
    public static class DefaultResourceLoader implements ResourceLoader {
        @Override
        public String load(String path) throws Exception {
            Resource resource = new ClassPathResource(path);
            return new String(Files.readAllBytes(resource.getFile().toPath()));
        }
    }
}


