package daangn.voting.repository;


import daangn.voting.domain.Board;
import daangn.voting.domain.Member;
import daangn.voting.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VoteRepository {

    private final EntityManager em;

    public void save(Vote vote) {
        em.persist(vote);
    }

    public Vote findOne(Long id) {
        return em.find(Vote.class, id);
    }

    public List<Vote> findBoard(Long boardId) {
        return em.createQuery("select v from Vote v inner join v.board b where b.id = :boardId", Vote.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public List<Vote> findAll() {
        return em.createQuery("select v from Vote v", Vote.class).getResultList();
    }

    public Vote findByName(String key) {
        return em.createQuery("select v from Vote v where v.key = :name", Vote.class)
                .setParameter("name", key).getResultList().get(0);
    }


}
