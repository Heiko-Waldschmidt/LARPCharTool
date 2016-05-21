package hwaldschmidt.larpchartool.bootstrap;

import hwaldschmidt.larpchartool.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Heiko Waldschmidt
 */
// disabled
//@Component
public class VisitLoader implements ApplicationListener<ContextRefreshedEvent> {

    private VisitRepository visitRepository;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


    }


}
