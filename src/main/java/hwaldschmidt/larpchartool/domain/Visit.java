package hwaldschmidt.larpchartool.domain;

import javax.persistence.*;
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
}
