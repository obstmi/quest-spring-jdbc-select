package com.wildcodeschool.wildandwizard.controller;

import com.wildcodeschool.wildandwizard.repository.WizardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WizardController {

    
	// Das WizardRepository fuehrt die DB-Abfrage durch und kapselt die Entitäten in Wizard-Objekten
	private WizardRepository repository = new WizardRepository();

    
	// Experiment, um die index-html im resources/templates-Ordner aufzurufen (läuft sonst - warum? - auf einen Fehler
	@GetMapping("/index.html")
	public String getIndex() {
		return "index";
	}
	
	// Route zur Wizards-All-Seite
	@GetMapping("/wizards")
    public String getAll(Model model) {

        model.addAttribute("wizards", repository.findAll());

        // Die html-Seite aus den "templates"
        return "wizard_get_all";
    }

	 // Route zu einem Wizard 
	@GetMapping("/wizard")  // Endpoint
    public String getById(Model model, @RequestParam Long id) {

        model.addAttribute("wizard", repository.findById(id));

        return "wizard_get";
    }

    @GetMapping("/wizards/search")
    //public String getByLastName(Model model, @RequestParam String lastName) {
    // Test mit default-value
    public String getByLastName(Model model, @RequestParam(required = false, 
            defaultValue = "%") String lastName) {


        model.addAttribute("wizards", repository.findByLastName(lastName));

        return "wizard_get_all";
    }
}
