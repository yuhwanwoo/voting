package daangn.voting.repository;

import daangn.voting.domain.VotedElementMember;
import daangn.voting.domain.VotedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VotedElementMemberRepository {

    private final EntityManager em;

    public void save(VotedElementMember votedElementMember) {
        em.persist(votedElementMember);
    }








}
