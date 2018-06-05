package com.example.k8s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probes")
public class ProbesController {

    private int healthCounter = 0;
    @GetMapping("/health")
    public String getHealth () {
        if(healthCounter > 10) throw new RuntimeException("not healthy");
        healthCounter += 1;
        return "ok";
    }
}
