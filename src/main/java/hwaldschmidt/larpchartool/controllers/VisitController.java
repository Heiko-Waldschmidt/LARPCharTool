package hwaldschmidt.larpchartool.controllers;

import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.services.CharaService;
import hwaldschmidt.larpchartool.services.ConventionService;
import hwaldschmidt.larpchartool.services.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    private static Logger logger = LoggerFactory.getLogger(VisitController.class.getName());
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
    public String listVisits(Model model){
        model.addAttribute("visits", visitService.listAllVisits());
        return "visits";
    }

    @RequestMapping("visit/{id}")
    public String showVisit(@PathVariable Integer id, Model model){
        model.addAttribute("visit", visitService.getVisitById(id));
        return "visitshow";
    }

    @RequestMapping("visit/edit/{id}")
    public String editVisit(@PathVariable Integer id, Model model){
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

    @RequestMapping(value = "visit/update", params = {"save"}, method = RequestMethod.POST)
    public String updateVisit(@Valid Visit visit, Model model, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            logger.error("visit/update binding result has errors");
            model.addAttribute("visit", visit);
            model.addAttribute("charas", charaService.listAllCharas());
            model.addAttribute("conventions", conventionService.listAllConventions());
            return "visitform";
        }
        if (isUniqueConstraintViolated(visit)){
            logger.error("visit/update: constraint is violated");
            bindingResult.rejectValue("chara", "chara", "A character cannot visit a convention more than once.");
            bindingResult.rejectValue("convention", "convention", "A character cannot visit a convention more than once.");
            model.addAttribute("visit", visit);
            model.addAttribute("charas", charaService.listAllCharas());
            model.addAttribute("conventions", conventionService.listAllConventions());
            return "visitform";
        }
        visitService.saveVisit(visit);
        return "redirect:/visits";
    }

    @RequestMapping("visit/delete/{id}")
    public String deleteVisit(@PathVariable Integer id){
        logger.debug("visit id to delete " + id);
        visitService.deleteVisit(id);
        return "redirect:/visits";
    }

    private boolean isUniqueConstraintViolated(Visit visit){
        Iterable<Visit> visits = visitService.listAllVisits();
        for(Visit anyVisit: visits){
            if ((visit.getId() == null || (visit.getId().intValue() != anyVisit.getId().intValue())) &&
                    visit.getChara().getId() == anyVisit.getChara().getId() &&
                    visit.getConvention().getId() == anyVisit.getConvention().getId()){
                        return true;
                    }
        }
        return false;
    }
}
