package hwaldschmidt.larpchartool.repositories;

import hwaldschmidt.larpchartool.domain.Convention;
import org.springframework.data.repository.CrudRepository;

/**
 * Marker interface to specialize the generic usage of CrudRepository. Crudrepository is used as a interface to the
 * database.
 *
 * @author Heiko Waldschmidt
 */
public interface ConventionRepository extends CrudRepository<Convention, Integer> {
}
