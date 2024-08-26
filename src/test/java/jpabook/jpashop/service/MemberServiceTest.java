package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    // @Rollback(false) -> commit이 되기 떄문에 영속성 컨텍스트가 flush 하면서 Insert 쿼리가 보임
    public void 회원가입() {
        // https://martinfowler.com/bliki/GivenWhenThen.html
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        // em.flush() -> @Transactional 때문에 실제 insert 되지는 않음
        Assertions.assertThat(memberService.findOne(savedId)).isEqualTo(member);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        fail("이미 존재하는 회원입니다.");
    }
}