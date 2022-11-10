package org.coronavisrustracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.coronavisrustracker.models.LocationStates;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaDataService {

    private static String  CORONA_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStates> locationStates = new ArrayList<>();
    @PostConstruct
    @Scheduled (cron = "* * * 1 * *")
    public void fetchData() throws IOException, InterruptedException {
         List<LocationStates> newStates = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CORONA_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request,HttpResponse.BodyHandlers.ofString());
        StringReader csvReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            LocationStates locationStates1 = new LocationStates();
            locationStates1.setState(record.get("Province/State"));
            locationStates1.setCountry(record.get("Country/Region"));
            locationStates1.setLatestTotalCases(record.get(record.size() - 1));
            System.out.println(locationStates1);
            newStates.add(locationStates1);
            this.locationStates = newStates;

        }

    }
}
