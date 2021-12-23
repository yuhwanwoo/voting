package daangn.voting.service;

import daangn.voting.domain.*;
import daangn.voting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ElementRepository elementRepository;
    private final VotedElementMemberRepository votedElementMemberRepository;
    private final VotedMemberRepository votedMemberRepository;

    public boolean validateDuplicateMember(String userName, String voteId) {
        Vote vote = voteRepository.findByName(voteId);
        List<VotedMember> findMembers = votedMemberRepository.findAll(userName,vote.getId());
        if (!findMembers.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean validateSize(List<Element> elements) {
        for (Element element : elements) {
            if(element.getName().length() > 50) {
                return false;
            }
        }
        return true;
    }

    public void validateDuplicateVote(Long boardId) {
        List<Vote> findVotes = voteRepository.findBoard(boardId);

        if (!findVotes.isEmpty()) {
            throw new IllegalStateException("이미 투표 존재");
        }

    }

    public Vote findOne(Long voteId) {
        return voteRepository.findOne(voteId);
    }

    public Vote findByName(String name) {
        return voteRepository.findByName(name);
    }

    // 투표 생성
    @Transactional
    public Long vote(Long boardId, String memberName, String name, String content, List<Element> elements, Long time){

        // 0보다 작을 때 에러 발생
        if (time < 0) {
            throw new IllegalStateException("0보다 작은 값이 입력됨");
        }

        // 0값이 들어왔을 때 default 값은 24시간
        if(time == 0) {
            time = 3600L;
        }

        Board board = boardRepository.findOne(boardId);
        Member member = memberRepository.findByName(memberName).get(0);

        Vote vote = Vote.createVote(board, member, name, content, time);

        for (Element element : elements) {
            Element element1 = new Element();
            element1.setVote(vote);
            element1.setName(element.getName());
            elementRepository.save(element1);
        }

        voteRepository.save(vote);

        return vote.getId();
    }

    // 투표 선택
    @Transactional
    public Long choice(Long elementId, String userName, String voteId) {
        Element element = elementRepository.findOne(elementId);
        Member member = memberRepository.findByName(userName).get(0);
        Vote vote = voteRepository.findByName(voteId);


        VotedMember votedMember = VotedMember.createOrder(vote, userName);
        VotedElementMember votedElementMember = VotedElementMember.createVotedMember(member.getName(), element);
        element.addCnt(1);
        votedElementMemberRepository.save(votedElementMember);
        votedMemberRepository.save(votedMember);


        return votedElementMember.getId();
    }

    public List<Element> findElements(Vote vote) {
        List<Element> elements = elementRepository.findAll(vote.getId());

        return elements;
    }


    public boolean compareTime(String voteId, LocalDateTime nowTime) {
        Vote vote = voteRepository.findByName(voteId);

        // 마감시간 보다 현재시간이 작으면 true 아니면 false
        boolean flag = nowTime.isBefore(vote.getLastTime());

        return flag;
    }
}
