package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
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
    public void paging(){
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 1;
        int limit =3;

        //when
        List<Member> members = memberRepository.findByPage(age, offset, limit);
        long totalCount = memberRepository.totalCount(age);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 22));
        memberRepository.save(new Member("member5", 43));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);

        //ten
        Assertions.assertThat(resultCount).isEqualTo(3);
    }



}

























