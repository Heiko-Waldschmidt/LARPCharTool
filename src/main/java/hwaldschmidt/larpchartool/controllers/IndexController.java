package hwaldschmidt.larpchartool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * These Controller is for the basic pages "/" and "/index"
 *
 * @author Heiko Waldschmidt
 */
@Controller
public class IndexController {

    /**
     * I dont want to make a difference between "/" and "/index" so i implement a redirect here.
     * @return
     */
    @RequestMapping("/")
    String root(){
        return "redirect:/index";
    }

    @RequestMapping("/index")
    String index(){
        return "index";
    }
}
