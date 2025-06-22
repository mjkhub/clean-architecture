package buckpal.hexagonal.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import buckpal.hexagonal.account.domain.Account;

interface AccountRepository extends JpaRepository<Account, Long> {


    Account findByAccountNumber(String accountNumber);
}
