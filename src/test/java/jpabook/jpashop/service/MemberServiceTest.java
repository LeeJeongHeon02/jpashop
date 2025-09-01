package jpabook.jpashop.service;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    // @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("ljh");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(saveId, member.getId());

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("ljh");
        Member member2 = new Member();
        member2.setName("ljh");

        //when
        memberRepository.save(member1);
//        memberRepository.save(member2);


        //then

        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // 예외가 발생해야함!

    }



}