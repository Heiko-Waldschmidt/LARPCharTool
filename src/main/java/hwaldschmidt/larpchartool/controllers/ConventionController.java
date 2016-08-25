package hwaldschmidt.larpchartool.controllers;

import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.services.ConventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for all views with a name beginning with "convention"
 *
 * @author Heiko Waldschmidt
 */
@Controller
public class ConventionController {

    private ConventionService conventionService;

    @Autowired
    public void setConventionService(ConventionService conventionService) {
        this.conventionService = conventionService;
    }

    @RequestMapping(value = "/conventions", method = RequestMethod.GET)
    public String listConventions(Model model){
        model.addAttribute("conventions", conventionService.listAllConventions());
        return "conventions";
    }

    @RequestMapping("convention/{id}")
    public String showConvention(@PathVariable Integer id, Model model){
        model.addAttribute("convention", conventionService.getConventionById(id));
        return "conventionshow";
    }

    @RequestMapping("convention/edit/{id}")
    public String editConvention(@PathVariable Integer id, Model model){
        model.addAttribute("convention", conventionService.getConventionById(id));
        return "conventionform";
    }

    @RequestMapping("convention/new")
    public String newConvention(Model model){
        model.addAttribute("convention", new Convention());
        return "conventionform";
    }

    @RequestMapping(value = "convention", method = RequestMethod.POST)
    public String saveConvention(Convention convention){
        conventionService.saveConvention(convention);
        return "redirect:/convention/" + convention.getId();
    }

    @RequestMapping("convention/delete/{id}")
    public String deleteConvention(@PathVariable Integer id){
        conventionService.deleteConvention(id);
        return "redirect:/conventions";
    }
}
