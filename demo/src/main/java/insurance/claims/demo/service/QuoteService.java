package insurance.claims.demo.service;

import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.AccountRepository;
import insurance.claims.demo.repository.QuoteRepository;
import insurance.claims.demo.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InsuranceQuoteGenerator insuranceQuoteGenerator;

    private Logger log = LoggerFactory.getLogger(QuoteService.class);

    public Quote generateQuote(QuoteRequest request) {


        Vehicle vehicle = vehicleRepository.findById(request.getVehicleID()).get();
        AppUser appUser = accountRepository.findById(request.getUserID()).get();

        QuoteParameters parameters = new QuoteParameters(
                appUser.getAge(),
                vehicle.getYearOfManufacture(),
                vehicle.getEstimatedValue(),
                vehicle.getColour(),
                vehicle.getFuelType()
        );
        //generating quotes using quotegenerator
        //Monthly value will not add up to annual intentionally
        HashMap<String, String> quotes = insuranceQuoteGenerator.createQuote(parameters);

        Quote quote = new Quote(
                vehicle.getVehicleID(),
                vehicle.getUserID(),
                appUser.getAge(),
                LocalDateTime.now().plusDays(5),
                Double.valueOf(quotes.get("yearlyAmount")),
                Double.valueOf(quotes.get("monthlyAmount")),
                false,
                vehicle.getRegistrationNumber()
        );

        quoteRepository.save(quote);
        log.info("Quote for "+vehicle.getRegistrationNumber()+" generated and saved");
        return quote;
    }

    public List<Quote> getQuotes(long userID) {

        return quoteRepository.findAllByuserID(userID);
    }

    //this method checks whether the user has an active quote for that particular car
    //quotes can only be generated again once the quote expires or if they reject it
    public boolean checkUserHasActiveQuote(long quoteID, long vehicleID) {

        Optional<Quote> quote =  quoteRepository
                .findByUserIDAndVehicleID(quoteID, vehicleID);

        if(quote.isEmpty()) return false;

        if(LocalDateTime.now().isBefore(quote.get().getQuoteExpiry())) return true;

        return false;
    }

}
