package com.personal.poc.reactivespring.controllers;

import com.personal.poc.reactivespring.models.Stock;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class ApplicationController {
    @GetMapping("")
    public String getDefaultPath() {
        return "/stocks";
    }

    @GetMapping(value = "/stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Stock> getStockPrices() {
        return Flux.fromStream(Stream.generate(this::getStocks))
                   .delayElements(Duration.ofSeconds(1));
    }

    private Stock getStocks() {
        return new Stock("GOOGLE", Math.random() * 1.5);
    }
}
