package buckpal.hexagonal.member.adapter.out.persistence;


import buckpal.hexagonal.member.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class MemberJpaRepository {

    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Optional<Member> findById(Long id){

        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findByLoginId(String loginId){

        List<Member> member = em.createQuery("select m from Member m join fetch m.accounts a " +
                        "where m.loginId =: loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();

        if(member.isEmpty()) return Optional.empty();
        else return Optional.of(member.get(0));
    }

}
