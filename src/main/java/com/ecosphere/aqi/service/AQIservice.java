package com.ecosphere.aqi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecosphere.aqi.model.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AQIservice {

    @Value("${openweather.api.key}")
    private String API_KEY;
    public AQIresponse getAQI(double lat, double lon) {

        try {
            String url = "https://api.openweathermap.org/data/2.5/air_pollution?lat="
                    + lat + "&lon=" + lon + "&appid=" + API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.getForObject(url, Map.class);
            System.out.println("API RESPONSE = " + response);

            List list = (List) response.get("list");
            Map main = (Map) ((Map) list.get(0)).get("main");

            Number aqiValue = (Number) main.get("aqi");
            int aqi = aqiValue.intValue();

            AQIresponse res = new AQIresponse();
            res.setAqi(aqi);
            res.setCategory(getCategory(aqi));

            return res;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("AQI fetch failed");

        }
    }

    // 🔥 NEW FEATURE: Compare AQI
    public AQICompareResponse compareAQI(double lat1, double lon1, double lat2, double lon2) {

        AQIresponse from = getAQI(lat1, lon1);
        AQIresponse to = getAQI(lat2, lon2);

        AQICompareResponse response = new AQICompareResponse();

        response.setFrom(from);
        response.setTo(to);

        int diff = to.getAqi() - from.getAqi();
        response.setDifference(diff);

        response.setRecommendation(getRecommendation(from.getAqi(), to.getAqi()));

        return response;
    }

    private String getCategory(int aqi) {
        switch (aqi) {
            case 1: return "Good";
            case 2: return "Fair";
            case 3: return "Moderate";
            case 4: return "Poor";
            case 5: return "Very Poor";
            default: return "Unknown";
        }
    }


    private String getRecommendation(int from, int to) {

        if (to <= 50) {
            return " Safe to travel. Air quality is good.";
        }
        else if (to <= 100) {
            return "Generally safe, but slightly polluted.";
        }
        else if (to <= 150) {
            return "⚠Caution advised for sensitive people.";
        }
        else if (to <= 200) {
            return "Not recommended unless necessary.";
        }
        else {
            return "Avoid travel. Severe pollution risk.";
        }
    }
}