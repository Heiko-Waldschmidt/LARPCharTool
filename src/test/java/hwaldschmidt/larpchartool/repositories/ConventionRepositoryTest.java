package hwaldschmidt.larpchartool.repositories;

import hwaldschmidt.larpchartool.domain.Convention;
import hwaldschmidt.larpchartool.configuration.ConventionConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ConventionConfiguration.class})
public class ConventionRepositoryTest {

    private ConventionRepository conventionRepository;

    @Autowired
    public void setConventionRepository(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    @Before
    public void setUp(){
        conventionRepository.deleteAll();
    }

    @Test
    public void testSaveConvention(){
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

        //get all conventions, list should only have one
        Iterable<Convention> conventions = conventionRepository.findAll();

        int count = 0;

        for(Convention c : conventions){
            count++;
        }

        assertEquals(count, 1);
    }
}
