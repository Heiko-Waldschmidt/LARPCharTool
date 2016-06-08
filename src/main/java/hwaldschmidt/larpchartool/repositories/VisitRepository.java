package hwaldschmidt.larpchartool.repositories;

import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Marker interface to specialize the generic usage of CrudRepository. Crudrepository is used as a interface to the
 * database.
 *
 * @author Heiko Waldschmidt
 */
public interface VisitRepository extends CrudRepository<Visit, Integer> {

    /*
     * Spring Data JPA is generating the implementation for this method!!!
     * http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
     */
    /**
     * Find the list of visits filtered by chara ordered by the start of the conventions
     * @param chara the chara to filter the visits for
     * @return Returns an iterable over all conventions the char has visited.
     */
    List<Visit> findByCharaOrderByConventionStartAsc(Chara chara);

    /**
     * Calculated the sum of the condays of the given char.
     * @param chara the given char
     * @return the sum of the condays
     */
    @Query("Select sum(v.condays) from Visit AS v where v.chara = ?1")
    Integer sumCondaysByChara(Chara chara);
}
