package buckpal.hexagonal.lottery.adapter.out.persistence;

import buckpal.hexagonal.lottery.domain.Lottery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LotteryJpaRepository {

    private final EntityManager em;

    public Lottery save(Lottery lottery){
        em.persist(lottery);
        return lottery;
    }


}
