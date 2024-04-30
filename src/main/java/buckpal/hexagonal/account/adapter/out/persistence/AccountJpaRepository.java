package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.QAccount;
import buckpal.hexagonal.member.domain.QMember;
import buckpal.hexagonal.transaction.domain.QTransaction;
import buckpal.hexagonal.transaction.domain.TransactionType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;


@Repository
@RequiredArgsConstructor
class AccountJpaRepository {

    private final EntityManager em;

    public Account findByNumber(String accountNumber){
        return em.createQuery("select a from Account a " +
                        "join fetch a.member m " +
                        "where a.number = :accountNumber ", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();
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

    public Account findWithTransactions(Long memberId, String accountNumber, String transactionType){
        List<Account> resultList = em.createQuery("select distinct a from Account a " +
                        "join a.member m " +
                        "join fetch a.transactions tr " +
                        "where a.number =:accountNumber and m.id =: memberId", Account.class)
                .setParameter("memberId", memberId)
                .setParameter("accountNumber", accountNumber)
                .getResultList();

        if(resultList.isEmpty()) //transaction 이 없으면 Account 자체가 조회가 안됨
            return findByNumber(accountNumber);
        else
            return resultList.get(0);
    }

    public Account findWithTransactionsQueryDsl(Long memberId, String accountNumber, String transactionType){

        QAccount account = QAccount.account;
        QMember member = QMember.member;
        QTransaction transaction = QTransaction.transaction;

        JPAQueryFactory query = new JPAQueryFactory(em);

        List<Account> resultList = query.select(account).distinct()
                .from(account)
                .join(account.member, member)
                .join(account.transactions, transaction).fetchJoin()
                .where(account.number.eq(accountNumber),
                        member.id.eq(memberId),
                        transactionTypeEq(transactionType))
                .fetch();

        if(resultList.isEmpty()) //transaction 이 없으면 Account 자체가 조회가 안됨
            return findByNumber(accountNumber);
        else
            return resultList.get(0);

    }

    private BooleanExpression transactionTypeEq(String transactionType){
        QTransaction transaction = QTransaction.transaction;
        if(!StringUtils.hasText(transactionType))
            return null;
        return transaction.transactionType.eq(TransactionType.getTransactionType(transactionType));
    }


}
