package chap02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // 엔티티 매니저 설정
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-book");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 API
        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    // 비즈니스 로직
    private static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("홍길동");
        member.setAge(7);

        em.persist(member); // 등록
        member.setAge(17);  // 수정

        // 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("이름 : " + findMember.getUsername() + ", 나이 : " + findMember.getAge());

        // 목록 조회
        List<Member> members =
                em.createQuery("select m from Member m", Member.class)
                        .getResultList();
        System.out.println("회원 데이터 : " + members.size());

        // 삭제
        em.remove(member);
    }
}