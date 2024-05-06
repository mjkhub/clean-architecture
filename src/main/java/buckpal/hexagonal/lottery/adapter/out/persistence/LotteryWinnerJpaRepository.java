package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.domain.LotteryWinner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LotteryWinnerJpaRepository {

    private final EntityManager em;

    public LotteryWinner save(LotteryWinner lotteryWinner){
        em.persist(lotteryWinner);
        return lotteryWinner;
    }

    public Long getEntityNumber(){
        return em.createQuery("select count(*) from LotteryWinner lw", Long.class)
                .getSingleResult();
    }

    public Optional<LotteryWinner> findById(Long lotteryCount){
        return Optional.ofNullable(em.find(LotteryWinner.class, lotteryCount));
    }


}
