package insurance.claims.demo.service;


import insurance.claims.demo.dto.QuoteParameters;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class InsuranceQuoteGenerator {
//    hashmap stores multipliers for quote parameters for now, should
//     be stored in separate file

    HashMap<String, Double> multipliers = new HashMap<>();

    public HashMap<String, Double> createQuote(QuoteParameters parameters) {

        //generating quote value by diving the cars value by
        // cumulative mulitpliers of the other parameters

        Double yearlyQuote = (parameters.getEstimatedValue())/
                (
                        multipliers.get(ageCategory(parameters.getDriverAge()))+
                        multipliers.get(parameters.getColour())+
                        multipliers.get(parameters.getFuelType())+
                        multipliers.get(yearCategory(parameters.getYearOfManufacture()))
                );

        Double monthlyQuote = ((yearlyQuote)*1.2)/12;

        HashMap<String, Double> finalQuotes = new HashMap<>();
        finalQuotes.put("yearlyAmount", yearlyQuote);
        finalQuotes.put("monthlyAmount", monthlyQuote);

        return finalQuotes;
    }


    public InsuranceQuoteGenerator(){
        multipliers.put("BLUE", 0.7);
        multipliers.put("GOLD", 0.3);
        multipliers.put("UNDER-25", 0.4);
        multipliers.put("OVER-25", 0.7);
        multipliers.put("OVER-30", 1.0);
        multipliers.put("BEFORE-2015", 0.5);
        multipliers.put("AFTER-2015", 0.9);

    }


    //converting age into a category
    public String ageCategory(int age) {
       if(age < 25) return "UNDER-25";
       if(age > 25 && age <30) return "OVER-25";
       if(age>30) return "OVER 30";

       return "";
    }

    //converting car year into category
    public String yearCategory(int year) {
        if(year<2015) return "BEFORE-2015";
        if(year>2015) return "AFTER-2015";

        return "";
    }

}
