package daangn.voting.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "VOTED_MEMBER_SEQ_GENERATOR",
        sequenceName = "VOTED_MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class VotedMember {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTED_MEMBER_SEQ_GENERATOR")
    @Column(name = "voted_member_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    public static VotedMember createOrder(Vote vote, String userName) {
        VotedMember votedMember = new VotedMember();
        votedMember.setVote(vote);
        votedMember.setName(userName);

        return votedMember;
    }
}
