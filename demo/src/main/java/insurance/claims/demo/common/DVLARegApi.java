package insurance.claims.demo.common;

import okhttp3.*;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.http.HttpClient;
import java.nio.Buffer;

//this class communicates with DVLA API to get vehicle information

@Component
public class DVLARegApi {

    private String API_KEY = "2QeIgM91qm7bKcK2JYHM83bGzq8VKIUi5Hg36jdq";
    private String API_URL = "https://driver-vehicle-licensing.api.gov.uk/vehicle-enquiry/v1/vehicles";

    public JSONObject getVehicleDetails(String registrationNumber) {


        OkHttpClient httpClient = new OkHttpClient();

        //creating mediatype and JSON object needed in the request body
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject postData = new JSONObject();

        //appending registration number of users car to request body
        try {
            postData.put("registrationNumber", registrationNumber);

        } catch (JSONException e) {
            e.printStackTrace();

        }

        //creating formBody and request for API call

        RequestBody formBody = RequestBody.create(JSON, postData.toString());
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("x-api-key", API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();

        //trying to make request
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code()!=200) throw new IllegalStateException("Car not found or invalid syntax");

            JSONObject responseObject = new JSONObject(response.body().string());
            return responseObject;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
