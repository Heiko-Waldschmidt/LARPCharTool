package hwaldschmidt.larpchartool.services;

import hwaldschmidt.larpchartool.domain.Visit;
import hwaldschmidt.larpchartool.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Heiko Waldschmidt
 */
@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Iterable<Visit> listAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Visit getVisitById(Integer id) {
        return visitRepository.findOne(id);
    }

    @Override
    public Visit saveVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void deleteVisit(Integer id) {
        visitRepository.delete(id);
    }
}
