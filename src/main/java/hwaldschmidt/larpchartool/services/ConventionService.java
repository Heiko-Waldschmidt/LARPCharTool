package hwaldschmidt.larpchartool.services;

import hwaldschmidt.larpchartool.domain.Convention;

/**
 * Interface for the service used to get convention data.
 *
 * @author Heiko Waldschmidt
 */
public interface ConventionService {
    Iterable<Convention> listAllConventions();

    Convention getConventionById(Integer id);

    Convention saveConvention(Convention convention);

    void deleteConvention(Integer id);
}
