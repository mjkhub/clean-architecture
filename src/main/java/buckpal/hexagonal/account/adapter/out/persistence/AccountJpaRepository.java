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

    public Account save(Account account){
        em.persist(account);
        return account;
    }

    public List<Account> findAccountsOfMember(Long memberId){
        return em.createQuery("select a from Account a " +
                        "join fetch a.member m " +
                        "where m.id =: memberId", Account.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Account findWithTransactions(Long memberId, String accountNumber){
        List<Account> resultList = em.createQuery("select distinct a from Account a " +
                        "join a.member m " +
                        "join fetch a.transactions tr " +
                        "where a.number =:accountNumber and m.id =: memberId", Account.class)
                .setParameter("memberId", memberId)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
        return resultList.get(0);
    }


}
