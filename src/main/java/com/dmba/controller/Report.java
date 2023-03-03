package com.dmba.controller;

import com.dmba.report.CustomerBySum;
import com.dmba.repository.UsersOrderRepository;
import com.dmba.repository.wrapper.UsersOrderRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/report")
public class Report {

    @Autowired
    private UsersOrderRepositoryWrapper usersOrderRepositoryWrapper;

//    @GetMapping("/topten")
//    public ResponseEntity<List<CustomerBySum>> getAllCustomers() {
//        List<CustomerBySum> result =  usersOrderRepositoryWrapper.findTop10UsersByOverallSum();
//        return ResponseEntity.ok(result);
//    }

    @GetMapping(value = "/topten", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<CustomerBySum> getAllCustomers() {
        return Flux.fromStream(usersOrderRepositoryWrapper.findTop10UsersByOverallSum()
                        .stream()
                        .map(result -> new CustomerBySum(result.getName(), result.getSum())))
//                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
