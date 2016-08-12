package hwaldschmidt.larpchartool.repositories;

import hwaldschmidt.larpchartool.configuration.VisitConfiguration;
import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.domain.Visit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {VisitConfiguration.class})
public class VisitRepositoryTest {

    private VisitRepository visitRepository;
    private ConventionRepository conventionRepository;
    private CharaRepository charaRepository;

    @Autowired
    public void setConventionRepository(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    @Autowired
    public void setCharaRepository(CharaRepository charaRepository) {
        this.charaRepository = charaRepository;
    }

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }

    @Before
    public void setUp(){
        charaRepository.deleteAll();
        conventionRepository.deleteAll();
        visitRepository.deleteAll();
    }

    @Test
    public void saveVisitTest() {
        Chara chara = new Chara();
        chara.setName("TestChara");
        assertNull(chara.getId());
        charaRepository.save(chara);
        assertNotNull(chara.getId());

        Chara fetchedChara = charaRepository.findOne(chara.getId());
        assertNotNull(fetchedChara);

        assertEquals(chara.getId(), fetchedChara.getId());
        assertEquals(chara.getName(), fetchedChara.getName());

        fetchedChara.setName("New TestChara");
        charaRepository.save(fetchedChara);

        //get from DB, should be updated
        Chara fetchedUpdatedChara = charaRepository.findOne(fetchedChara.getId());
        assertEquals(fetchedChara.getName(), fetchedUpdatedChara.getName());

        //verify count of charas in DB
        long charaCount = charaRepository.count();
        assertEquals(charaCount, 1);

        Convention convention = new Convention();
        convention.setTitle("Title");
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

        //update description and save
        fetchedConvention.setTitle("New Title");
        conventionRepository.save(fetchedConvention);

        //get from DB, should be updated
        Convention fetchedUpdatedConvention = conventionRepository.findOne(fetchedConvention.getId());
        assertEquals(fetchedConvention.getTitle(), fetchedUpdatedConvention.getTitle());

        //verify count of conventions in DB
        long conventionCount = conventionRepository.count();
        assertEquals(conventionCount, 1);

        Visit visit = new Visit();
        visit.setChara(fetchedUpdatedChara);
        visit.setConvention(fetchedUpdatedConvention);
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
    }
}
