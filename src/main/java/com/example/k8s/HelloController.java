package com.example.k8s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping()
    public String get(HttpServletRequest req) {
        return String.format("Hello from %s at %s", req.getLocalName(), req.getLocalAddr());
    }

}
