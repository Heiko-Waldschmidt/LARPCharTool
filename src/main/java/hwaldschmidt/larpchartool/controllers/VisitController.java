package hwaldschmidt.larpchartool.controllers;

import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.services.CharaService;
import hwaldschmidt.larpchartool.services.ConventionService;
import hwaldschmidt.larpchartool.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Controller for all Views with a name beginning with "visit".
 * @author Heiko Waldschmidt
 */
@Controller
public class VisitController {
    private VisitService visitService;
    private CharaService charaService;
    private ConventionService conventionService;

    @Autowired
    public void setConventionService(ConventionService conventionService) {
        this.conventionService = conventionService;
    }

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setCharaController(CharaService charaService){
        this.charaService = charaService;
    }

    @RequestMapping(value = "visits", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("visits", visitService.listAllVisits());
        return "visits";
    }

    @RequestMapping("visit/{id}")
    public String showVisit(@PathVariable Integer id, Model model){
        model.addAttribute("visit", visitService.getVisitById(id));
        return "visitshow";
    }

    @RequestMapping("visit/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("visit", visitService.getVisitById(id));
        model.addAttribute("charas", charaService.listAllCharas());
        model.addAttribute("conventions", conventionService.listAllConventions());
        return "visitform";
    }

    @RequestMapping(value = "visit/new")
    public String newVisit(Model model){
        model.addAttribute("visit", new Visit());
        model.addAttribute("charas", charaService.listAllCharas());
        model.addAttribute("conventions", conventionService.listAllConventions());
        return "visitform";
    }

    @RequestMapping(value = "visit/update", method = RequestMethod.POST)
    public ModelAndView updateVisit(@Valid Visit visit, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            System.err.println(bindingResult.toString());
            return new ModelAndView("redirect:/visit/edit/" + visit.getId());
        }
        visitService.saveVisit(visit);
        return new ModelAndView("redirect:/visits");
    }

    @RequestMapping("visit/delete/{id}")
    public String deleteVisit(@PathVariable Integer id){
        System.err.println("Id to delete " + id);
        visitService.deleteVisit(id);
        return "redirect:/visits";
    }
}
