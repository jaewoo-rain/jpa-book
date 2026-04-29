package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v2/member")
    public CreateMemberResponse saveMember(
            @RequestBody @Valid CreateMemberRequest request
    ){

        Member member = new Member();
        member.setName(request.getName());
        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long memberId) {
            this.id = memberId;
        }
    }

    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String name;
    }

    @PutMapping("/api/v2/member/{memberId}")
    public UpdateMemberResponse updateMember(
            @PathVariable(name = "memberId") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ){
        String name = memberService.updateMember(id, request.getName());
        return new UpdateMemberResponse(id, name);
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @GetMapping("/api/v2/member")
    public Result members(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(m -> new MemberDto(m.getName()))
                .toList(); // 수정 불가능
        return new Result(collect);

        // 수정 가능하게 할때
//        List<MemberDto> re = members.stream()
//                .map(m -> new MemberDto(m.getName()))
//                .collect(Collectors.toList());

    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


}
