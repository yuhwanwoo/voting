package daangn.voting.repository;

import daangn.voting.domain.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElementRepository {

    private final EntityManager em;

    public void save(Element element) {
        em.persist(element);
    }

    public Element findOne(Long id) {
        return em.find(Element.class, id);
    }

    public List<Element> findAll(Long voteId) {
        return em.createQuery("select e from Element e inner join e.vote v where v.id = :voteId  ", Element.class)
                .setParameter("voteId", voteId)
                .getResultList();
    }


}
