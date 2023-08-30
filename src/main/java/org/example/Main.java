package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // [엔티티 매니저 팩토리] - 생성
        // persistence.xml의 설정 정보를 사용해서 엔티티 매니저 팩토리를 생성
        // (이름이 jpa_lab_1인 영속성 유닛을 찾아서 엔티티 매니저 팩토리를 생성한다)
        // 엔티티 매니저 팩토리는 애플리케이션 전체에서 딱 한번만 생성하고, 공유해서 사용해야 한다.
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa_lab_1");
        // [엔티티 매니저] - 생성
        // 엔티티 메니저 팩토리에서 엔티티 매니저를 생성한다. JPA의 기능 대부분은 엔티티 매니저가 제공한다.
        // 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
        // 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안된다.
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        // JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야 한다.
        // 트랜잭션 없이 데이터를 변경하면 예외가 발생한다.
        EntityTransaction tx = em.getTransaction();

        try {
            // [트랜잭션 API]를 사용하여 비즈니스 로직이 정상 동작하면 트랜잭션을 Commit 하고,
            // 예외가 발생하면 트랜잭션을 Rollback 한다.
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

    private static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 등록
        em.persist(member);

        // 수정
        // em.update()라는 메소드는 없다.
        // 단순히 엔티티의 값만 변경해도, JPA는 어떤 엔티티가 변경 되었는지 추적하는 기능을 갖추고 있다.
        // 엔티티의 값만 변경하면 UPDATE SQL을 생성해서 데이터베이스에 값을 변경한다.
        member.setAge(20);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        List<Member> members =
                    em.createQuery("select m from Member m", Member.class)
                            .getResultList();
        System.out.println("members.size=" + members.size());

        // 삭제
        em.remove(member);
    }

}