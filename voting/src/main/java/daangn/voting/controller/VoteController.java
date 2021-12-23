package daangn.voting.controller;

import daangn.voting.domain.*;
import daangn.voting.service.VoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    // 투표 생성
    @PostMapping("api/v1/votes")
    public CreateVoteResponse saveVote(@RequestHeader(value = "x-user-id") String userName, @RequestBody @Valid CreateVoteRequest request) {

        voteService.validateDuplicateVote(request.boardId);

        // 투표 항목 개수 판별
        if(request.elements.size() < 2 || request.elements.size() > 100) {
            return new CreateVoteResponse("투표 항목은 최소 2개 최대 100여야 합니다.(실패)");
        }

        voteService.validateSize(request.elements);
        Long id = voteService.vote(request.boardId, userName, request.name, request.content, request.elements, request.time);
        Vote vote = voteService.findOne(id);
        return new CreateVoteResponse(vote.getKey());
    }

    // 투표 조회
    @GetMapping("api/v1/votes")
    public VoteResponse votes(@RequestHeader(value = "x-user-id") String userName, @RequestBody @Valid VoteRequest request) {

        Vote findVote = voteService.findByName(request.voteId);

        boolean flag = voteService.validateDuplicateMember(userName, request.voteId);

        List<Element> elements = voteService.findElements(findVote);
        List<ElementDto> collect = elements.stream().map(o -> new ElementDto(o)).collect(Collectors.toList());

        List<VotedElementDto> counts = elements.stream().map(o -> new VotedElementDto(o)).collect(Collectors.toList());

        return new VoteResponse(findVote.getMember().getName(), findVote.getName(), findVote.getContent(), collect, findVote.getLastTime(), flag, counts);
    }

    // 투표 선택
    @PostMapping("api/v1/choice")
    public ChoiceResponse choice(@RequestHeader(value = "x-user-id") String userName, @RequestBody @Valid ChoiceRequest request) {

        // 이미 참여했는지 여부 판단
        boolean flag = voteService.validateDuplicateMember(userName, request.voteId);

        String result;

        LocalDateTime nowTime = LocalDateTime.now();

        // 시간이 지났는지 판단하기 위한 boolean
        boolean compareTime = voteService.compareTime(request.voteId, nowTime);

        if (compareTime == true) { // 마감 시각이 지나지 않음
            if (flag == true) {
                result = "이미 투표하였습니다.";
            } else {
                result = "성공";
                voteService.choice(request.elementId, userName, request.voteId);
            }
        } else { // 마감 시각이 지남
            result = "마감 시간이 지났습니다.";
        }

        return new ChoiceResponse(result);
    }

    @Data
    private class ElementDto {
        private Long id;
        private String name;

        public ElementDto(Element element) {
            id = element.getId();
            name = element.getName();
        }
    }

    @Data
    private class VotedElementDto {
        private Long id;
        private String name;
        private int cnt;

        public VotedElementDto(Element element) {
            id = element.getId();
            name = element.getName();
            cnt = element.getCount();
        }
    }

    @Data
    static class CreateVoteRequest {
        @NotNull
        private Long boardId;
        @NotNull
        @Size(max = 100)
        private String name;
        @Size(max = 10000)
        private String content;
        @NotNull
        private List<Element> elements;
        @NotNull
        private Long time;
    }


    @Data
    static class CreateVoteResponse {
        private String id;

        public CreateVoteResponse(String id) {
            this.id = id;
        }
    }

    @Data
    static class VoteRequest {
        @NotNull
        private Long boardId;
        @NotNull
        private String voteId;
    }

    @Data
    static class VoteResponse {

        private String userId;
        private String name;
        private String content;
        private List<ElementDto> elements;
        private LocalDateTime lastTime;
        private boolean flag;
        private List<VotedElementDto> count;

        public VoteResponse(String userId, String name, String content, List<ElementDto> elements, LocalDateTime lastTime, boolean flag, List<VotedElementDto> count) {
            this.userId = userId;
            this.name = name;
            this.content = content;
            this.elements = elements;
            this.lastTime = lastTime;
            this.flag = flag;
            this.count = count;
        }
    }

    @Data
    static class ChoiceResponse {
        String message;

        public ChoiceResponse(String message) {
            this.message = message;
        }
    }

    @Data
    static class ChoiceRequest {
        @NotNull
        private Long boardId;
        @NotNull
        private String voteId;
        @NotNull
        private Long elementId;

    }
}
