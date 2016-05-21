package hwaldschmidt.larpchartool.bootstrap;

import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.repositories.CharaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Heiko Waldschmidt
 */
// disabled
//@Component
public class CharaLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CharaRepository charaRepository;

    private Logger log = Logger.getLogger(ConventionLoader.class);

    @Autowired
    public void setCharaRepository(CharaRepository charaRepository) {
        this.charaRepository = charaRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Chara chara = new Chara();
        chara.setName("Testchar 1");
        charaRepository.save(chara);

        Chara chara2 = new Chara();
        chara2.setName("Testchar 2");
        charaRepository.save(chara2);
    }
}
