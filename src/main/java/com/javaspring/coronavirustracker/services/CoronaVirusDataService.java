package com.javaspring.coronavirustracker.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.javaspring.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {

    private Date lastUpdateOn;

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_DATA_URL_DEAD = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static String VIRUS_DATA_URL_Recovered = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();
    private List<LocationStats> allStatsDead = new ArrayList<>();
    private List<LocationStats> allStatsRecovered = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* 1 * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        this.setLastUpdateOn(new Date());
        String data = sendGET(VIRUS_DATA_URL);
        this.allStats = convertData(data);

        data = sendGET(VIRUS_DATA_URL_DEAD);
        this.setAllStatsDead(convertData(data));

        data = sendGET(VIRUS_DATA_URL_Recovered);
        this.setAllStatsRecovered(convertData(data));
    }

    public List<LocationStats> convertData(String data) throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        StringReader csvBodyReader = new StringReader(data);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setLatitude(record.get("Lat"));
            locationStat.setLongitude(record.get("Long"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        Collections.sort(newStats);
        return newStats;
    }

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String sendGET(String VIRUS_DATA_URL) throws IOException {
        URL obj = new URL(VIRUS_DATA_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append("\n");
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            return "GET request not worked";
        }

    }

    public List<LocationStats> getAllStatsDead() {
        return allStatsDead;
    }

    public void setAllStatsDead(List<LocationStats> allStatsDead) {
        List<LocationStats> newStats = new ArrayList<>();
        for (LocationStats ls : getAllStats()) {
            for (LocationStats lsDead : allStatsDead) {
                if (ls.getLatitude().equalsIgnoreCase(lsDead.getLatitude())
                        && ls.getLongitude().equalsIgnoreCase(lsDead.getLongitude())
                        && ls.getCountry().equalsIgnoreCase(lsDead.getCountry())) {
                    ls.setLatestTotalDeadCases(lsDead.getLatestTotalCases());

                }

            }
            newStats.add(ls);
        }
        this.allStats = newStats;
        this.allStatsDead = allStatsDead;
    }

    public List<LocationStats> getAllStatsRecovered() {
        return allStatsRecovered;
    }

    public void setAllStatsRecovered(List<LocationStats> allStatsRecovered) {
        List<LocationStats> newStats = new ArrayList<>();
        for (LocationStats ls : getAllStats()) {
            for (LocationStats lsRecovered : allStatsRecovered) {
                if (ls.getLatitude().equalsIgnoreCase(lsRecovered.getLatitude())
                        && ls.getLongitude().equalsIgnoreCase(lsRecovered.getLongitude())
                        && ls.getCountry().equalsIgnoreCase(lsRecovered.getCountry())) {
                    ls.setLatestTotalRecoveredCases(lsRecovered.getLatestTotalCases());

                }

            }
            newStats.add(ls);
        }
        this.allStats = newStats;
        this.allStatsRecovered = allStatsRecovered;
    }

    public Date getLastUpdateOn() {
        return lastUpdateOn;
    }

    public void setLastUpdateOn(Date lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;
    }

}
