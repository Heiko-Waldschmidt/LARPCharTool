package hwaldschmidt.larpchartool.charactersheetwriter;

import hwaldschmidt.larpchartool.domain.Chara;
import hwaldschmidt.larpchartool.domain.Visit;

import java.io.IOException;
import java.util.List;

/**
 * An exporter exports chara data as a human readable file.
 *
 * @author Heiko Waldschmidt
 */
public interface CharacterSheetWriter {

    /**
     * Export char data to human readable file.
     * @param chara data about this chara will be exported.
     * @param visits the visits this chara has done.
     * @param condays the condays of this chara.
     * @return the filename of the character sheet
     * @throws IOException if file could not be written
     */
    public String createCharacterSheet(Chara chara, List<Visit> visits, int condays) throws IOException;
}
