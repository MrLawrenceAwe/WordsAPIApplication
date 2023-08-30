package com.LawrenceAwe.artifact;

import com.hubspot.jinjava.Jinjava;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Service
public class TemplateService {
    private final Jinjava jinjava;
    private final ResourceLoader resourceLoader;

    public TemplateService() {
        this(new Jinjava(), new ResourceLoader());
    }

    public TemplateService(Jinjava jinjava, ResourceLoader resourceLoader) {
        this.jinjava = jinjava;
        this.resourceLoader = resourceLoader;
    }

    public String renderTemplate(String templatePath, Map<String, Object> contextMap) throws IOException {
        String template = resourceLoader.load(templatePath);
        return jinjava.render(template, contextMap);
    }

    public static class ResourceLoader {
        public String load(String path) throws IOException, IllegalArgumentException {
            try {
                Resource resource = new ClassPathResource(path);
                return new String(Files.readAllBytes(resource.getFile().toPath()));
            } catch (NullPointerException e) {
                throw new IOException("File object is null", e);
            }
        }
    }
}


