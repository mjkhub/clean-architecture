package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.domain.Lottery;
import buckpal.hexagonal.member.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LotteryJpaRepository {

    private final EntityManager em;

    public Lottery save(Lottery lottery){
        em.persist(lottery);
        return lottery;
    }

    public List<Lottery> findAllLottery(Long lotteryCount){
        return em.createQuery("select l from Lottery l " +
                "where l.lotteryCount =:lotteryCount", Lottery.class)
                .setParameter("lotteryCount", lotteryCount)
                .getResultList();
    }

    public Optional<Lottery> findLotteryWithMember(Member member, Long lotteryCount){ // 재 사용성 Up

        List<Lottery> lottery = em.createQuery("select l from Lottery l join fetch l.member m " +
                        "where m.id =: memberId and " +
                        "l.lotteryCount =: lotteryCount", Lottery.class)
                .setParameter("memberId", member.getId())
                .setParameter("lotteryCount", lotteryCount)
                .getResultList(); //result should be one or zero

        if(lottery.isEmpty()) return Optional.empty();
        else return Optional.of(lottery.get(0));
    }



}
