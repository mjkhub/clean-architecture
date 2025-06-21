package buckpal.hexagonal.transaction.application.port.in;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.application.service.TransactionService;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import buckpal.hexagonal.transaction.domain.Transaction;
import buckpal.hexagonal.transaction.domain.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class TransactionUseCaseTest {

    @Autowired
    private TransactionCrudPort transactionCrudPort;

    @Autowired
    private AccountCrudPort accountCrudPort;

    @Autowired
    private TransactionUseCase transactionUseCase;

    @BeforeEach
    void setUp() {
        // Initialize two accounts with balances
        Account sourceAccount = Account.builder()
                .accountNumber("1111-1111")
                .balance(10000)
                .build();
        Account destinationAccount = Account.builder()
                .accountNumber("2222-2222")
                .balance(5000)
                .build();

        accountCrudPort.saveAccount(sourceAccount);
        accountCrudPort.saveAccount(destinationAccount);
    }

    @Test
    void transferMoney_successfulTransfer() {
        // Arrange
        TransactionRequest request = new TransactionRequest("1111-1111", "2222-2222", 2000);

        // Act
        int updatedBalance = transactionUseCase.transferMoney(request);

        // Assert
        Account sourceAccount = accountCrudPort.findByAccountNumber("1111-1111");
        Account destinationAccount = accountCrudPort.findByAccountNumber("2222-2222");

        assertEquals(8000, sourceAccount.getBalance());
        assertEquals(7000, destinationAccount.getBalance());
        assertEquals(8000, updatedBalance);

        // Verify transactions
        List<Transaction> sourceTransactions = transactionCrudPort.findAllByAccount(sourceAccount);
        List<Transaction> dstTransactions = transactionCrudPort.findAllByAccount(destinationAccount);
        assertEquals(1, sourceTransactions.size()); // withdrawal and deposit transactions
        assertEquals(1, dstTransactions.size()); // withdrawal and deposit transactions
    }

    @Test
    void transferMoney_insufficientFunds() {
        // Arrange
        TransactionRequest request = new TransactionRequest("1111-1111", "2222-2222", 15000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionUseCase.transferMoney(request));

        // Ensure balances remain unchanged
        Account sourceAccount = accountCrudPort.findByAccountNumber("1111-1111");
        Account destinationAccount = accountCrudPort.findByAccountNumber("2222-2222");

        assertEquals(10000, sourceAccount.getBalance());
        assertEquals(5000, destinationAccount.getBalance());
    }
}