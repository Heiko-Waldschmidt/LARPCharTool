package hwaldschmidt.larpchartool.repositories;

import hwaldschmidt.larpchartool.configuration.CharaConfiguration;
import hwaldschmidt.larpchartool.domain.Chara;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CharaConfiguration.class})
public class CharaRepositoryTest {

    private CharaRepository charaRepository;

    @Autowired
    public void setCharaRepository(CharaRepository charaRepository) {
        this.charaRepository = charaRepository;
    }

    @Test
    public void saveCharacterTest(){
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

        //get all charas, list should only have one
        Iterable<Chara> charas = charaRepository.findAll();

        int count = 0;

        for(Chara c : charas){
            count++;
        }

        assertEquals(count, 1);
    }
}
