package com.LawrenceAwe.artifact;

import com.hubspot.jinjava.Jinjava;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.util.Map;

public class TemplateService {
    private final Jinjava jinjava;

    public TemplateService() {
        this.jinjava = new Jinjava();
    }

    public String renderTemplate(String templatePath, Map<String, Object> context) throws Exception {
        String template = new String(Files.readAllBytes(new ClassPathResource(templatePath).getFile().toPath()));
        return jinjava.render(template, context);
    }
}

