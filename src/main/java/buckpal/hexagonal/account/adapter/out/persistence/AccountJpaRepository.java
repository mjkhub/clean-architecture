package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.domain.Account;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
@RequiredArgsConstructor
class AccountJpaRepository {

    private final EntityManager em;

    public Account findByName(String accountName){
        List<Account> resultList = em.createQuery("select a from Account a where a.number = :name", Account.class)
                .setParameter("name", accountName)
                .getResultList();
        return resultList.get(0);
    }

    public void update(String name, int money){
        Account accountJpaEntity = this.findByName(name);
        accountJpaEntity.updateMoney(money);
        em.flush();
    }

    public Account save(Account account){
        em.persist(account);
        return account;
    }



//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void initAccount(){
//        AccountJpaEntity jay = new AccountJpaEntity("jay", "1234",10000, LocalDate.now());
//        AccountJpaEntity park = new AccountJpaEntity("park","1234" ,20000,LocalDate.now());
//        em.persist(jay);
//        em.persist(park);
//        em.flush();
//        em.clear();
//    }

}
