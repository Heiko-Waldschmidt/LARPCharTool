package hwaldschmidt.larpchartool.domain;

import javax.persistence.*;
import java.util.List;

/**
 * The character/role you play.
 *
 * @author Heiko Waldschmidt
 */
@Entity
public class Chara {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    @Column(unique=true, nullable=false)
    private String name;

    @OneToMany(mappedBy = "chara", cascade = CascadeType.ALL)
    private List<Visit> visits;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Visit> getVisits (){
        return visits;
    }

    public void setVisits(List<Visit> visits){
        this.visits = visits;
    }

    // TODO add visits required?
}
