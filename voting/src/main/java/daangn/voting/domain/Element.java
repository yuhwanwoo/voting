package daangn.voting.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "ELEMENT_SEQ_GENERATOR",
        sequenceName = "ELEMENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Element {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ELEMENT_SEQ_GENERATOR")
    @Column(name = "element_id")
    private Long id;

    private String name;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_uid")
    private Vote vote;

    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    private List<VotedElementMember> votedElementMembers = new ArrayList<>();


    public void addCnt(int cnt) {
        this.count += cnt;
    }
}
