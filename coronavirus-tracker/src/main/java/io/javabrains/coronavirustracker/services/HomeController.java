package io.javabrains.coronavirustracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService cvd;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<ReportRepo> data = cvd.getStats();
		int totalCasesToday = data.stream().mapToInt(i -> i.getLatestCount()).sum();
		int totalNewCases = data.stream().mapToInt(i -> i.getDifference()).sum();
		model.addAttribute("records", data );
		model.addAttribute("totalCasesToday", totalCasesToday);
		model.addAttribute("totalNewCases", totalNewCases);
		return "home";
	}
}
