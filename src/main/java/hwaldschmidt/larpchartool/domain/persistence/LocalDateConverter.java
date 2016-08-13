package hwaldschmidt.larpchartool.domain.persistence;

import java.time.LocalDate;
import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This converter is automatically used when a Date needs to be saved into the database or is read from database. With
 * the help of this class we can use the new LocalDate instead of sql.Date everywhere in the code.
 *
 * @author Heiko Waldschmidt
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return (date == null ? null : Date.valueOf(date));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }
}