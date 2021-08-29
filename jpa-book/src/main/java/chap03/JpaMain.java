package chap03;

import javax.persistence.*;
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
//            logic(em);
//            testDetached(em);
//            clearEntityManager(em);
            closeEntityManager(em, tx);
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

//        em.setFlushMode(FlushModeType.COMMIT); // 플러시 모드 : 트랜잭션을 커밋할 때만 플러시를 수행, 성능 최적화

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

    // 영속 상태 -> 준영속 상태
    public static void testDetached(EntityManager em) {

        // 회원 엔티티 생성, 비영속 상태
        String id = "testMember";
        Member member = new Member();
        member.setId(id);
        member.setUsername("테스트회원");
        
        em.persist(member); // 영속
        em.detach(member);  // 영속 -> 준영속

        // 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("이름 : " + findMember.getUsername());
    }

    // 영속성 컨텍스트 초기화
    public static void clearEntityManager(EntityManager em) {

        // 조회
        String id = "id2";
        Member findMember = em.find(Member.class, id);
        System.out.println("이름 : " + findMember.getUsername());
        em.clear(); // 초기화

        findMember.setUsername("변경된홍길동");
        System.out.println("이름 : " + findMember.getUsername());
    }

    // 영속성 컨텍스트 종료
    public static void closeEntityManager(EntityManager em, EntityTransaction tx) {

        // 조회
        String id = "id1";
        Member findMember1 = em.find(Member.class, id);
        System.out.println("이름 : " + findMember1.getUsername());

        tx.commit();
        em.close(); // 영속성 컨텍스트 종료

        // 조회 : 준영속 상태
        findMember1.setUsername("다길동");
        System.out.println("이름 : " + findMember1.getUsername());
    }
}