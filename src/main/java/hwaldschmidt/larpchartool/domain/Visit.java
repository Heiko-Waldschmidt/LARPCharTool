package hwaldschmidt.larpchartool.domain;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * You visit a convention with a character. The information about the visit is hold here.
 *
 * @author Heiko Waldschmidt
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"chara_id", "convention_id"})})
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    @NotNull
    @Column(nullable = false)
    private Short condays;

    @ManyToOne
    @JoinColumn(name="chara_id")
    @NotNull
    private Chara chara;

    @ManyToOne
    @JoinColumn(name="convention_id")
    @NotNull
    private Convention convention;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Short getCondays() {
        return condays;
    }

    public void setCondays(Short condays) {
        this.condays = condays;
    }

    public Chara getChara() {
        return chara;
    }

    public void setChara(Chara chara) {
        this.chara = chara;
    }

    public Convention getConvention() {
        return convention;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", version=" + version +
                ", condays=" + condays +
                ", chara=" + chara +
                ", convention=" + convention +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Visit visit = (Visit) other;

        if (id != null ? !id.equals(visit.id) : visit.id != null) return false;
        if (version != null ? !version.equals(visit.version) : visit.version != null) return false;
        if (condays != null ? !condays.equals(visit.condays) : visit.condays != null) return false;
        if (chara != null ? !chara.equals(visit.chara) : visit.chara != null) return false;
        return convention != null ? convention.equals(visit.convention) : visit.convention == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (condays != null ? condays.hashCode() : 0);
        result = 31 * result + (chara != null ? chara.hashCode() : 0);
        result = 31 * result + (convention != null ? convention.hashCode() : 0);
        return result;
    }
}
