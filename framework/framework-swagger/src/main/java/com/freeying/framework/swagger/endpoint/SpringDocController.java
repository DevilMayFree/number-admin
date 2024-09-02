package com.freeying.framework.swagger.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * SpringDocController
 *
 * @author fx
 */
@RestController
public class SpringDocController {

    /**
     * favicon.ico
     */
    @GetMapping(value = "/favicon.ico", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(hidden = true)
    public ResponseEntity<InputStreamResource> favicon() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/favicon.png");
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
