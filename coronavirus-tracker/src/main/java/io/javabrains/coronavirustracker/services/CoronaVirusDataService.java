package io.javabrains.coronavirustracker.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaVirusDataService {

	private List<ReportRepo> stats = new ArrayList<>();
	
	

	public List<ReportRepo> getStats() {
		return stats;
	}



	@PostConstruct
	@Scheduled(cron = "0 0 6 * * ?")
	public void fetchFreshData() throws IOException {
		List<ReportRepo> newStats = new ArrayList<>();
		URL url;
		url = new URL(
				"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder data = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				data = data.append('\n');
				data = data.append(inputLine);
			}
			in.close();
			StringReader csvInput = new StringReader(data.toString());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvInput);

			for (CSVRecord record : records) {
				ReportRepo report = new ReportRepo();
				report.setProvince(record.get("Province/State"));
				report.setCountry(record.get("Country/Region"));
				report.setLatestCount(Integer.parseInt(record.get(record.size() - 1)));
				int yesterdayCases = Integer.parseInt(record.get(record.size() - 2));
				report.setDifference(report.getLatestCount()-yesterdayCases);
				newStats.add(report);
			}
			this.stats = newStats;
		} else {
			System.out.println("GET request not worked");
		}
	}

}
