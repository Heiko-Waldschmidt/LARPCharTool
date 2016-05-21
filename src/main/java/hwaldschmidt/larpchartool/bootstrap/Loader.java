package hwaldschmidt.larpchartool.bootstrap;

import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.repositories.CharaRepository;
import hwaldschmidt.larpchartool.repositories.ConventionRepository;
import hwaldschmidt.larpchartool.repositories.VisitRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * The Loader puts some basic data into the database.
 *
 * @author Heiko Waldschmidt
 */
@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = Logger.getLogger(ConventionLoader.class);

    private CharaRepository charaRepository;
    private ConventionRepository conventionRepository;
    private VisitRepository visitRepository;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Autowired
    public void setCharaRepository(CharaRepository charaRepository) {
        this.charaRepository = charaRepository;
    }

    @Autowired
    public void setConventionRepository(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    /**
     * This is called during startup via spring-boot and constructs some basic data and puts it into the database.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Chara chara1 = new Chara();
        chara1.setName("Testchar 1");
        chara1 = charaRepository.save(chara1);

        Chara chara2 = new Chara();
        chara2.setName("Testchar 2");
        chara2 = charaRepository.save(chara2);

        Convention convention1 = new Convention();
        convention1.setStart(new Date(100, 0, 1));
        convention1.setEnd(new Date(100, 11, 31));
        convention1.setTitle("Test-Convention 1");
        convention1.setDf(false);
        convention1 = conventionRepository.save(convention1);

        Convention convention2 = new Convention();
        convention2.setStart(new Date(101, 0, 1));
        convention2.setEnd(new Date(101, 11, 31));
        convention2.setTitle("Test-Convention 2");
        convention2.setDf(true);
        convention2 = conventionRepository.save(convention2);

        Convention convention3 = new Convention();
        convention3.setStart(new Date(102, 0, 1));
        convention3.setEnd(new Date(102, 11, 31));
        convention3.setTitle("Test-Convention 3");
        convention3.setDf(false);
        convention3 = conventionRepository.save(convention3);

        Visit visit1 = new Visit();
        visit1.setChara(chara1);
        visit1.setConvention(convention1);
        visit1.setCondays((short) 1);
        visit1 = visitRepository.save(visit1);

        Visit visit2 = new Visit();
        visit2.setChara(chara1);
        visit2.setConvention(convention3);
        visit2.setCondays((short) 3);
        visit2 = visitRepository.save(visit2);

        Visit visit3 = new Visit();
        visit3.setChara(chara2);
        visit3.setConvention(convention2);
        visit3.setCondays((short) 2);
        visit3 = visitRepository.save(visit3);

        Visit visit4 = new Visit();
        visit4.setChara(chara2);
        visit4.setConvention(convention3);
        visit4.setCondays((short) 3);
        visit4 = visitRepository.save(visit4);
    }
}
