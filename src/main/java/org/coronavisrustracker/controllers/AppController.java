package org.coronavisrustracker.controllers;

import org.coronavisrustracker.services.CoronaDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    private CoronaDataService coronaDataService;

    public AppController(CoronaDataService coronaDataService) {
        this.coronaDataService = coronaDataService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("states",coronaDataService.getLocationStates());
        return "home";
    }
}
