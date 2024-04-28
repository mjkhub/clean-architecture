package buckpal.hexagonal.transaction.adapter.out;


import buckpal.hexagonal.transaction.domain.Transaction;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class TransactionJpaRepository {

    private final EntityManager em;

    public Transaction save(Transaction transaction){
        em.persist(transaction);
        return transaction;
    }
}
