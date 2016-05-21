package hwaldschmidt.larpchartool.bootstrap;

import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.repositories.ConventionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.sql.Date;

/**
 * @author Heiko Waldschmidt
 */
// disabled
//@Component
public class ConventionLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ConventionRepository conventionRepository;

    private Logger log = Logger.getLogger(ConventionLoader.class);

    @Autowired
    public void setConventionRepository(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Convention convention = new Convention();
        convention.setStart(new Date(100,0,1));
        convention.setEnd(new Date(100,11,31));
        convention.setTitle("Test-Convention 1");
        convention.setDf(false);
        conventionRepository.save(convention);

        Convention convention2 = new Convention();
        convention2.setStart(new Date(101,0,1));
        convention2.setEnd(new Date(101,11,31));
        convention2.setTitle("Test-Convention 2");
        convention2.setDf(false);
        conventionRepository.save(convention2);

        Convention convention3 = new Convention();
        convention3.setStart(new Date(102,0,1));
        convention3.setEnd(new Date(102,11,31));
        convention3.setTitle("Test-Convention 3");
        convention3.setDf(false);
        conventionRepository.save(convention3);
    }
}
