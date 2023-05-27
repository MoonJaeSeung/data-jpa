package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testMember(){
        System.out.println("memberRepository. = " + memberRepository.getClass());
        Member member = new Member("씨발년아");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();//optional은 있을수도 있고 없을 수도 있는 원통

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member = new Member("이강인");
        Member member12 = new Member("김민재");
        memberRepository.save(member);
        memberRepository.save(member12);

        //단건 조회 검증
        Member findMember11 = memberRepository.findById(member.getId()).get();
        Member findMember21 = memberRepository.findById(member12.getId()).get();

        assertThat(findMember11).isEqualTo(member);
        assertThat(findMember21).isEqualTo(member12);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member);
        memberRepository.delete(member12);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("김민재", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("김민재", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("김민재");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void findHelloBy(){
        List<Member> helloBy = memberRepository.findTop3HelloaBy();
    }

    @Test
    public void testNamedQuery(){
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("이강인", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("김민재");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void testQuery(){
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("이강인", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("김민재",10);
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void findUsernameList(){
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("이강인", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto(){
        Team team = new Team("manU");
        teamRepository.save(team);

        Member m1 = new Member("김민재", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("이강인", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("김민재", "이강인"));
        for (Member member : result) {
            System.out.println("member = " + member);

        }
    }

    @Test
    public void returnType(){
        Member m1 = new Member("김민재", 10);
        Member m2 = new Member("이강인", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        Member 김민재 = memberRepository.findMemberByUsername("씨발련아");
        System.out.println("김민재 = " + 김민재);
        
        
        
    }
}