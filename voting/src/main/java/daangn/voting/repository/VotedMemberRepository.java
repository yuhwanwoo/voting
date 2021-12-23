package daangn.voting.repository;

import daangn.voting.domain.VotedElementMember;
import daangn.voting.domain.VotedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VotedMemberRepository {

    private final EntityManager em;


    public void save(VotedMember votedMember) {
        em.persist(votedMember);
    }

    public List<VotedMember> findAll(String userName, Long voteId) {
        System.out.println(userName);
        System.out.println(voteId);
        return em.createQuery("select vm from VotedMember vm inner join vm.vote v where v.id = :voteId and vm.name = :userName", VotedMember.class)
                .setParameter("userName", userName)
                .setParameter("voteId", voteId)
                .getResultList();
    }

    public List<VotedMember> findVotedAll(Long voteId) {
        return em.createQuery("select vm from VotedMember vm inner join vm.vote v where v.id = :voteId", VotedMember.class)
                .setParameter("voteId", voteId)
                .getResultList();
    }
}
