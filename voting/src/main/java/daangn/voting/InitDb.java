package daangn.voting;

import daangn.voting.domain.Board;
import daangn.voting.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("useA");
            em.persist(member);

            Board board = new Board();
            board.setMember(member);
            board.setName("board1");
            em.persist(board);
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("useB");
            em.persist(member);

            Board board = new Board();
            board.setMember(member);
            board.setName("board2");
            em.persist(board);
        }
    }
}
