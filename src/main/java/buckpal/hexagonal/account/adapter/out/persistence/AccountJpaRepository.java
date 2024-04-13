package buckpal.hexagonal.account.adapter.out.persistence;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RequiredArgsConstructor
class AccountJpaRepository {

    private final EntityManager em;

    public AccountJpaEntity findByName(String accountName){
        List<AccountJpaEntity> resultList = em.createQuery("select a from AccountJpaEntity a where a.name = :name", AccountJpaEntity.class)
                .setParameter("name", accountName)
                .getResultList();
        return resultList.get(0);
    }

    public void update(String name, int money){
        AccountJpaEntity accountJpaEntity = this.findByName(name);
        accountJpaEntity.updateMoney(money);
        em.flush();
    }








    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initAccount(){
        AccountJpaEntity jay = new AccountJpaEntity("jay", "1234",10000);
        AccountJpaEntity park = new AccountJpaEntity("park","1234" ,20000);
        em.persist(jay);
        em.persist(park);
        em.flush();
        em.clear();
    }

}
