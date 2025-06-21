package buckpal.hexagonal.account.adapter.out.persistence;

import buckpal.hexagonal.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

interface AccountRepository extends JpaRepository<Account, Long> {


    Account findByAccountNumber(String accountNumber);
}
