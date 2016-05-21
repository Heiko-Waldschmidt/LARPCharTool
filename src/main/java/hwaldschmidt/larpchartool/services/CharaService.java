package hwaldschmidt.larpchartool.services;

import hwaldschmidt.larpchartool.domain.Chara;

/**
 * Interface for the service used to get chara data.
 *
 * @author Heiko Waldschmidt
 */
public interface CharaService {
    Iterable<Chara> listAllCharas();

    Chara getCharaById(Integer id);

    Chara saveChara(Chara chara);

    void deleteChara(Integer id);
}
