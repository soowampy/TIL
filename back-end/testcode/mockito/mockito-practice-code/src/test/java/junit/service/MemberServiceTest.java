package junit.service;

import junit.model.Member;
import junit.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MemberServiceTest {

    @Test
    void 멤버_생성_테스트(){
        MemberRepository memberRepository = mock(MemberRepository.class);
        MemberService memberService = new MemberService(memberRepository);

        String name = "홍길동";
        Member member = memberService.createMember(name);
        assertThat(member.getName()).isEqualTo(name);
    }
}
