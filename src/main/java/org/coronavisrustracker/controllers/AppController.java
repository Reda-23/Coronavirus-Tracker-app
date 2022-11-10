package org.coronavisrustracker.controllers;

import org.coronavisrustracker.models.LocationStates;
import org.coronavisrustracker.services.CoronaDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {
    private final CoronaDataService coronaDataService;

    public AppController(CoronaDataService coronaDataService) {
        this.coronaDataService = coronaDataService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStates> allStats = coronaDataService.getLocationStates();
        int totalReportedCases = allStats.stream().mapToInt(stats -> Integer.parseInt(stats.getLatestTotalCases())).sum();
        int totalNewCases = allStats.stream().mapToInt(stats -> stats.getDiffFromPrevDay()).sum();
        model.addAttribute("states",coronaDataService.getLocationStates());
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
