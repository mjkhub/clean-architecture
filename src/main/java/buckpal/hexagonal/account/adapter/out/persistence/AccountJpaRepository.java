package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.domain.Account;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
class AccountJpaRepository {

    private final EntityManager em;

    public Account findByNumber(String accountNumber){
        List<Account> resultList = em.createQuery("select a from Account a " +
                        "join fetch a.member m " +
                        "where a.number = :accountNumber ", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
        return resultList.get(0);
    }

    public void updateMoney(Long id, int money){
        Account account = em.find(Account.class, id);
        account.updateMoney(money);
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
