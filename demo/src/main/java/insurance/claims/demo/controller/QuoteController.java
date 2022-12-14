package insurance.claims.demo.controller;

import insurance.claims.demo.dto.QuoteRequest;
import insurance.claims.demo.dto.QuoteResponse;
import insurance.claims.demo.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;
    @PostMapping(path = "/get")
    public ResponseEntity<?> generateQoute(@RequestBody QuoteRequest quoteRequest) {

        return ResponseEntity.ok(quoteService.generateQuote(quoteRequest));
    }

    @GetMapping(path = "/getQuotes/{userID}")
    public ResponseEntity<?> getQuotes(@PathVariable("userID") long userID) {

        return ResponseEntity.ok(quoteService.getQuotes(userID));
    }

    @PostMapping(path = "/quoteResponse")
    public ResponseEntity<?> quoteResponse(@RequestBody QuoteResponse response) {

        return ResponseEntity.ok(quoteService.quoteResponse(response));
    }
}
