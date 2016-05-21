package hwaldschmidt.larpchartool.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The convention you visited.
 *
 * @author Heiko Waldschmidt
 */
@Entity
public class Convention {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL)
    private List<Visit> visits;

    @Column(unique=true, nullable=false)
    private String title;
    private Date start;
    private Date end;

    private boolean df = false;

    public boolean isDf() {
        return df;
    }

    public void setDf(boolean df) {
        this.df = df;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Visit> getVisits() {
        return visits;
    }
}
