package daangn.voting.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "VOTE_SEQ_GENERATOR",
        sequenceName = "VOTE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Vote {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTE_SEQ_GENERATOR")
    @Column(name = "uid")
    private Long id;

    @Column(name = "vote_id")
    private String key;

    @Size(max = 100)
    private String name;

    @Size(max = 100000)
    private String content;

    // 생성시간
    private LocalDateTime voteTime;
    
    // 마감시간
    private LocalDateTime lastTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<Element> elements = new ArrayList<>();

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<VotedMember> votedMembers = new ArrayList<>();



    //==생성 메서드==//
    public static Vote createVote(Board board, Member member, String name, String content, Long time) {
        Vote vote = new Vote();
        vote.setBoard(board);
        vote.setMember(member);
        vote.setName(name);
        vote.setContent(content);
        vote.setVoteTime(LocalDateTime.now());

        LocalDateTime endingTime = LocalDateTime.now();
        endingTime = endingTime.plusMinutes(time);

        vote.setLastTime(endingTime);

        // 문자열 10자리 생성
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        vote.setKey(generatedString);

        return vote;
    }
}
