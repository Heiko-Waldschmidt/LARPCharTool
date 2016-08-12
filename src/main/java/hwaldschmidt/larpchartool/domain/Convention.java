package hwaldschmidt.larpchartool.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
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

//    @NotNull
    @Column(unique=true, nullable=false)
    private String title;

    //@NotNull
    // Creates a converter for string to localeDate conversion. Needed since I use LocalDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    //@NotNull
    // Creates a converter for string to localeDate conversion. Needed since I use LocalDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    // I don't compare the visits here ... this is to complicated ... I can't use a simple equals because of an endless
    // loop. Need to compare Ids only and prevent that one Id can be added only once.
    // So I need to change the list to a sorted set first then when i want to make it save but I can't find the right
    // method for this now, which is working with every jpa implementation.
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Convention that = (Convention) other;

        if (df != that.df) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (df ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Convention{" +
                "id=" + id +
                ", version=" + version +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", df=" + df +
                '}';
    }
}
