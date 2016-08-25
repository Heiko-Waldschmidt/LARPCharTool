package hwaldschmidt.larpchartool.controllers;

import hwaldschmidt.larpchartool.charactersheetwriter.CharacterSheetWriter;
import hwaldschmidt.larpchartool.charactersheetwriter.PdfCharacterSheetWriter;
import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.services.CharaService;
import hwaldschmidt.larpchartool.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller for all Views with a name beginning with "chara".
 *
 * @author Heiko Waldschmidt
 */
@Controller
public class CharaController {

    private CharaService charaService;
    private VisitService visitService;
    private CharacterSheetWriter characterSheetWriter;

    @Autowired
    public void setCharacterSheetWriter(CharacterSheetWriter characterSheetWriter){
        this.characterSheetWriter = characterSheetWriter;
    }

    @Autowired
    public void setVisitService(VisitService visitService){
        this.visitService = visitService;
    }

    @Autowired
    public void setCharaService(CharaService charaService) {
        this.charaService = charaService;
    }

    @RequestMapping(value = "/charas", method = RequestMethod.GET)
    public String listCharas(Model model){
        model.addAttribute("charas", charaService.listAllCharas());
        return "charas";
    }

    @RequestMapping("chara/{id}")
    public String showChara(@PathVariable Integer id, Model model){
        Chara chara = charaService.getCharaById(id);
        model.addAttribute("chara", chara);
        model.addAttribute("visits", visitService.findByCharaOrderByConventionStartAsc(chara));
        model.addAttribute("condays", visitService.sumCondaysByChara(chara));
        return "charashow";
    }

    @RequestMapping(value = "chara/{id}/charactersheet")
    @ResponseBody
    public FileSystemResource getCharacterSheet(@PathVariable Integer id, HttpServletResponse response){
        Chara chara = charaService.getCharaById(id);
        String filename = "";
        try {
            filename = characterSheetWriter.createCharacterSheet(
                    chara,
                    visitService.findByCharaOrderByConventionStartAsc(chara),
                    visitService.sumCondaysByChara(chara)
            );
        } catch (Exception e){
            // TODO exception handling
            // Exception is already logged, but we need to show the error to the use in the webinterface
            e.printStackTrace();
        }
        response.setContentType("application/pdf");
        // let the browser download and not show the file
        response.setHeader("Content-Disposition", "attachment; filename=" + chara.getName() + ".pdf");
        return new FileSystemResource(filename);
    }

    @RequestMapping("chara/edit/{id}")
    public String editChara(@PathVariable Integer id, Model model){
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
        return "redirect:/charas";
    }

    @RequestMapping("chara/delete/{id}")
    public String deleteChara(@PathVariable Integer id){
        charaService.deleteChara(id);
        return "redirect:/charas";
    }
}
