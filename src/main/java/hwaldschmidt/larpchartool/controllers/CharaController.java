package hwaldschmidt.larpchartool.controllers;

import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.services.CharaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for all Views with a name beginning with "chara".
 *
 * @author Heiko Waldschmidt
 */
@Controller
public class CharaController {

    private CharaService charaService;

    @Autowired
    public void setCharaService(CharaService charaService) {
        this.charaService = charaService;
    }

    @RequestMapping(value = "/charas", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("charas", charaService.listAllCharas());
        return "charas";
    }

    @RequestMapping("chara/{id}")
    public String showChara(@PathVariable Integer id, Model model){
        model.addAttribute("chara", charaService.getCharaById(id));
        return "charashow";
    }

    @RequestMapping("chara/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("chara", charaService.getCharaById(id));
        return "charaform";
    }

    @RequestMapping("chara/new")
    public String newChara(Model model){
        model.addAttribute("chara", new Chara());
        return "charaform";
    }

    @RequestMapping(value = "chara", method = RequestMethod.POST)
    public String saveChara(Chara chara){
        charaService.saveChara(chara);
        return "redirect:/chara/" + chara.getId();
    }

    @RequestMapping("chara/delete/{id}")
    public String deleteChara(@PathVariable Integer id){
        charaService.deleteChara(id);
        return "redirect:/charas";
    }
}
