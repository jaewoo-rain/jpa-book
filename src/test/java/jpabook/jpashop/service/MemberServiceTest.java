package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("memberA");
        Address address = new Address("도시", "동", "코드");
        member.setAddress(address);

        // when
        Long savedMemberId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedMemberId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getAddress()).isEqualTo(member.getAddress());
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("member1");

        Member member2 = new Member();
        member2.setName("member1");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    }

}