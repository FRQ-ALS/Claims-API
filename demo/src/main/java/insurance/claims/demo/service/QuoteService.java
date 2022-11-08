package insurance.claims.demo.service;

import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.AccountRepository;
import insurance.claims.demo.repository.InsuranceRepository;
import insurance.claims.demo.repository.QuoteRepository;
import insurance.claims.demo.repository.VehicleRepository;
import net.bytebuddy.description.type.TypeList;
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

    @Autowired
    private InsuranceRepository insuranceRepository;

    private Logger log = LoggerFactory.getLogger(QuoteService.class);

    public Quote generateQuote(QuoteRequest request) {

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleID()).get();
        AppUser appUser = accountRepository.findById(request.getUserID()).get();

        if(vehicle.isInsured()) throw new IllegalStateException("Vehicle already insured");

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
        log.info("Quote for " + vehicle.getRegistrationNumber() + " generated and saved");
        return quote;
    }

    public List<Quote> getQuotes(long userID) {

        return quoteRepository.findAllByuserIDAndUserAcceptance(userID, false);
    }

    //this method checks whether the user has an active quote for that particular car
    //quotes can only be generated again once the quote expires or if they reject it
    public boolean checkUserHasActiveQuote(long quoteID, long vehicleID) {

        Optional<Quote> quote = quoteRepository
                .findByUserIDAndVehicleID(quoteID, vehicleID);

        if (quote.isEmpty()) return false;

        if (LocalDateTime.now().isBefore(quote.get().getQuoteExpiry())) return true;

        return false;
    }


    public GenericResponse quoteResponse(QuoteResponse response) {
        Optional<Quote> quote = quoteRepository.findById(response.getQuoteID());

        //firstly checking if quote is still valid

        if (quote.isEmpty()) return new GenericResponse(false, "This quote could not be found");

        if (quote.get().getQuoteExpiry().isBefore(LocalDateTime.now())) {
            return new GenericResponse(false, "Sorry, this quote has expired");
        }


        //if quote is valid, create new insurance

        Insurance insurance = new Insurance(
                response.getUserID(),
                quote.get().getVehicleID(),
                response.getQuoteID(),
                LocalDateTime.now().plusDays(365),
                response.getPaymentType(),
                decidePaymentAmount(quote.get(), response.getPaymentType())
        );

        insuranceRepository.save(insurance);


        //updating selected vehicle to make it insured
        vehicleRepository.setCarInsuredToTrue(quote.get().getVehicleID());

        //updating quote to indicate that the user has accepted it
        quoteRepository.setUserAcceptance(response.getQuoteID());

        return new GenericResponse(true, "Congratulations, your insurance has been created!");
    }


    //method to decide what payment amount to choose for insurance instance

    public double decidePaymentAmount(Quote quote, String type) {
        if (type.equals("annually")) return quote.getYearlyQuoteValue();

        if (type.equals("monthly")) return quote.getMonthlyQuoteValue();

        return 0.0;
    }


}
