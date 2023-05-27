package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        Team teamA = new Team("ManU");
        Team teamB = new Team("ManCity");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("Kim", 10, teamA);
        Member member12 = new Member("bruno", 20, teamA);
        Member member13 = new Member("KDB", 30, teamB);
        Member member14 = new Member("HOLLAND", 40, teamB);

        em.persist(member1);
        em.persist(member12);
        em.persist(member13);
        em.persist(member14);

        //초기화
        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());

        }
    }
}