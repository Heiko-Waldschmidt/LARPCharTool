package hwaldschmidt.larpchartool.charactersheetwriter;

import hwaldschmidt.larpchartool.ChartoolWebApplication;
import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.repositories.CharaRepository;
import hwaldschmidt.larpchartool.repositories.ConventionRepository;
import hwaldschmidt.larpchartool.repositories.VisitRepository;
import hwaldschmidt.larpchartool.services.CharaService;
import hwaldschmidt.larpchartool.services.VisitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Heiko Waldschmidt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ChartoolWebApplication.class)
@WebAppConfiguration
public class CreatePDFTest {

    private CharaRepository charaRepository;
    private VisitRepository visitRepository;
    private ConventionRepository conventionRepository;
    private CharaService charaService;
    private VisitService visitService;
    private Chara chara;

    @Autowired
    public void setCharaRepository(CharaRepository charaRepository) {
        this.charaRepository = charaRepository;
    }

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Autowired
    public void setConventionRepository(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    @Autowired
    public void setCharaService(CharaService charaService){
        this.charaService = charaService;
    }

    @Autowired
    public void setVisitService(VisitService visitService){
        this.visitService = visitService;
    }

    @Before
    public void init(){
        charaRepository.deleteAll();
        conventionRepository.deleteAll();
        visitRepository.deleteAll();

        chara = new Chara();
        chara.setName("Merlin");
        assertNull(chara.getId());
        charaRepository.save(chara);
        assertNotNull(chara.getId());

        Chara fetchedChara = charaRepository.findOne(chara.getId());
        assertNotNull(fetchedChara);

        assertEquals(chara.getId(), fetchedChara.getId());
        assertEquals(chara.getName(), fetchedChara.getName());

        //verify count of charas in DB
        long charaCount = charaRepository.count();
        assertEquals(charaCount, 1);

        Convention convention = new Convention();
        convention.setTitle("Convention 1");
        convention.setDf(false);
        convention.setStart(LocalDate.of(2000, 1, 1));
        convention.setEnd(LocalDate.of(2000, 12, 31));
        assertNull(convention.getId()); //null before save
        conventionRepository.save(convention);
        assertNotNull(convention.getId()); // not null after save
        //fetch from DB
        Convention fetchedConvention = conventionRepository.findOne(convention.getId());

        //should not be null
        assertNotNull(fetchedConvention);

        //should equal
        assertEquals(convention.getId(), fetchedConvention.getId());
        assertEquals(convention.getTitle(), fetchedConvention.getTitle());

        //verify count of conventions in DB
        long conventionCount = conventionRepository.count();
        assertEquals(conventionCount, 1);

        Visit visit = new Visit();
        visit.setChara(chara);
        visit.setConvention(convention);
        visit.setCondays(new Short("3"));
        assertNull(visit.getId());
        visitRepository.save(visit);
        assertNotNull(visit.getId());

        Visit fetchedVisit = visitRepository.findOne(visit.getId());
        assertNotNull(fetchedVisit);

        assertEquals(visit.getId(), fetchedVisit.getId());
        assertEquals(visit.getConvention(), fetchedVisit.getConvention());
        assertEquals(visit.getChara(), fetchedVisit.getChara());
        assertEquals(visit.getCondays(), fetchedVisit.getCondays());

        assertEquals(1, visitRepository.count());

        for (int i = 2; i <= 100; ++i){
            addConventionWithVisit(
                    chara,
                    "Convention " + i,
                    new Short("2"),
                    LocalDate.of(2000 + i, 2, 2),
                    LocalDate.of(2000 + i, 11, 30),
                    false
            );
        }

        assertEquals(100, visitRepository.count());
    }

    private void addConventionWithVisit(Chara chara, String conName, Short condays, LocalDate start, LocalDate end,
                                        boolean df){
        Convention convention = new Convention();
        convention.setTitle(conName);
        convention.setDf(df);
        convention.setStart(start);
        convention.setEnd(end);
        assertNull(convention.getId()); //null before save
        conventionRepository.save(convention);

        Visit visit = new Visit();
        visit.setChara(chara);
        visit.setConvention(convention);
        visit.setCondays(condays);
        visitRepository.save(visit);
    }

    /**
     * This is not really an automated test, but I don't like to check the generated pdf automatically. This is too
     * much work for a hobby project. This test is more a helper for a manual tests of the CharacterSheetWriter.
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        Iterable<Visit> iterable = visitRepository.findAll();
        for (Visit visit: iterable){
            System.out.println(visit.toString());
        }
        createPDF(chara);
    }

    private void createPDF(Chara chara) throws IOException {
        List<Visit> visitList = visitService.findByCharaOrderByConventionStartAsc(chara);
        int condays = visitService.sumCondaysByChara(chara);
        CharacterSheetWriter sheetWriter = new PdfCharacterSheetWriter();
        String filename = sheetWriter.createCharacterSheet(chara, visitList, condays);
        System.out.println("created file with name: " + filename);
    }
}
