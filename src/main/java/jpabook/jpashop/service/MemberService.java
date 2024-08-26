package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // springframework 패키지 쪽 transactional이 더 많은 옵션 가능

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 X -> 약간의 성능 향상
public class MemberService {
    private final MemberRepository memberRepository; // 테스트 케이스 등 초기화 안 한 경우 컴파일 시점에 알려줌

    /**
     * 회원 가입
     * @param member
     * @return id
     */
    @Transactional // 변경 O, 읽기 전용 X
    public Long join(Member member) {
        isDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // save()에서 persist 할 때 id가 db에 없더라도 영속성 컨텍스트에 올라가면서 id 값이 저장됨
    }

    private void isDuplicateMember(Member member) {
        if (!memberRepository.findByName(member.getName()).isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     * @return members
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
