package com.javaspring.coronavirustracker.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javaspring.coronavirustracker.models.LocationStats;
import com.javaspring.coronavirustracker.services.CoronaVirusDataService;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("lastUpdateOn", coronaVirusDataService.getLastUpdateOn());
        //model.addAttribute("totalCountry", allStats.stream().distinct().collect(Collectors.toList()));

        List<LocationStats> allStatsDead = coronaVirusDataService.getAllStatsDead();
        int totalReportedCasesDead = allStatsDead.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCasesDead = allStatsDead.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("totalReportedDeadCases", totalReportedCasesDead);
        model.addAttribute("totalNewDeadCases", totalNewCasesDead);

        List<LocationStats> allStatsRecovered = coronaVirusDataService.getAllStatsRecovered();
        int totalReportedCaseRecovered = allStatsRecovered.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCasesRecovered = allStatsRecovered.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("totalReportedRecoveredCases", totalReportedCaseRecovered);
        model.addAttribute("totalNewRecoveredCases", totalNewCasesRecovered);

        model.addAttribute("mortalityRate", "" + (float) ((float) totalReportedCasesDead / (float) totalReportedCases));

        return "home";
    }
}
