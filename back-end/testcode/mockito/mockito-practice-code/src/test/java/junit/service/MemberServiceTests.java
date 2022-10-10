package junit.service;

import junit.model.Member;
import junit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTests {
    @Test
    void 멤버_생성_테스트(@Mock MemberRepository memberRepository){
        MemberService memberService = new MemberService(memberRepository);
        String name = "홍길동";
        Member member = memberService.createMember(name);
        assertThat(member.getName()).isEqualTo(name);
    }

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    void 멤버_생성_테스트(){
        String name = "홍길동";
        Member member = memberService.createMember(name);
        assertThat(member.getName()).isEqualTo(name);
    }


    @Test
    void 멤버_생성_메서드가_1번_호출됐는지_확인(){
        String name = "홍길동";
        memberService.createMember(name);
        verify(memberRepository, times(1)).save(any(Member.class));
    }


    @Test
    void 멤버_확인_테스트() {
        String name = "김영희";

        // 기대 행위 작성
        when(memberService.findMember(1L))
                .thenReturn(Optional.of(new Member(1L, "김영희")));

        Optional<Member> findMember = memberService.findMember(1L);
        assertThat(findMember.get().getName()).isEqualTo(name);
    }

}
