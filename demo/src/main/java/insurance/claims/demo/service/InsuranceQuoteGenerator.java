package insurance.claims.demo.service;


import insurance.claims.demo.dto.QuoteParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;

@Service
public class InsuranceQuoteGenerator {
//    hashmap stores multipliers for quote parameters for now, should
//     be stored in separate file

    HashMap<String, Double> multipliers = new HashMap<>();

    Logger log = LoggerFactory.getLogger(InsuranceQuoteGenerator.class);

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public HashMap<String, String> createQuote(QuoteParameters parameters) {

        //generating quote value by diving the cars value by
        // cumulative mulitpliers of the other parameters

        Double yearlyQuote = (parameters.getEstimatedValue())/
                (
                        multipliers.get(ageCategory(parameters.getDriverAge()))+
                        multipliers.get(parameters.getColour())+
                        multipliers.get(parameters.getFuelType())+
                        multipliers.get(yearCategory(parameters.getYearOfManufacture()))
                );

        log.info("Generated yearlyQuote of :" + yearlyQuote);

        Double monthlyQuote = ((yearlyQuote)*1.5)/12;

        log.info("Generated monthlyQuote of :" + monthlyQuote);

        HashMap<String, String> finalQuotes = new HashMap<>();
        finalQuotes.put("yearlyAmount", df.format(yearlyQuote));
        finalQuotes.put("monthlyAmount", df.format(monthlyQuote));

        return finalQuotes;
    }

    //generating a hasMap with parameters that affect the insurance cost and their score
    //the more expensive a parameter, the lower its score
    public InsuranceQuoteGenerator(){
        multipliers.put("BLUE", 0.7);
        multipliers.put("GOLD", 0.3);
        multipliers.put("RED", 0.7);
        multipliers.put("BLACK", 0.9);
        multipliers.put("GREY", 0.8);
        multipliers.put("UNDER-25", 0.4);
        multipliers.put("OVER-25", 0.7);
        multipliers.put("OVER-30", 1.0);
        multipliers.put("BEFORE-2015", 0.5);
        multipliers.put("AFTER-2015", 0.9);
        multipliers.put("PETROL", 0.5);
        multipliers.put("DIESEL", 0.7);

    }


    //converting age into a category (just a draft)
    public String ageCategory(int age) {
       if(age < 25) return "UNDER-25";
       if(age > 25 && age <30) return "OVER-25";
       if(age>30) return "OVER 30";
       return "";
    }

    //converting car year into category (just a draft)
    public String yearCategory(int year) {
        if(year<2015) return "BEFORE-2015";
        if(year>2015) return "AFTER-2015";

        return "";
    }

}
