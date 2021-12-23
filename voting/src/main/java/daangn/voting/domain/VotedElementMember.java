package daangn.voting.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "VOTE_ELEMENT_MEMBER_SEQ_GENERATOR",
        sequenceName = "VOTE_ELEMENT_MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class VotedElementMember {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTE_ELEMENT_MEMBER_SEQ_GENERATOR")
    @Column(name = "voted_element_member_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id")
    private Element element;

    //==생성 메서드==//
    public static VotedElementMember createVotedMember(String name, Element element) {
        VotedElementMember votedElementMember = new VotedElementMember();
        votedElementMember.setName(name);
        votedElementMember.setElement(element);

        return votedElementMember;


    }

}
