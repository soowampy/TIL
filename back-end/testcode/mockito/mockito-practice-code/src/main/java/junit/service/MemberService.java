package junit.service;

import junit.model.Member;
import junit.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(String name){
        Member member = new Member(2L, name);
        memberRepository.save(member);
        sendMail();
        return member;
    }

    public void sendMail(){
        System.out.println("성공");
    }

    public Optional<Member> findMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member;
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
