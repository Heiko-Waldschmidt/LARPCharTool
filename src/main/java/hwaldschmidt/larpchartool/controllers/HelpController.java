package hwaldschmidt.larpchartool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller for the "/help" view
 *
 * @author Heiko Waldschmidt
 */
@Controller
public class HelpController {

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/help")
    String help(Model model){
        model.addAttribute("env", applicationContext.getEnvironment());
        return "help";
    }
}
