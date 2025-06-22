package buckpal.hexagonal.transaction.application.service;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.application.port.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import buckpal.hexagonal.transaction.domain.Transaction;
import buckpal.hexagonal.transaction.domain.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final TransactionCrudPort transactionCrudPort;
    private final AccountCrudPort accountCrudPort;

    @Transactional
    public int transferMoney(TransactionRequest tr){

        Account sourceAccount = accountCrudPort.findByAccountNumber(tr.getSourceAccountNumber());
        Account destinationAccount = accountCrudPort.findByAccountNumber(tr.getDestinationAccountNumber());

        int amount = tr.getAmount();
        sourceAccount.subMoney(amount);
        destinationAccount.addMoney(amount);

        LocalDateTime now = LocalDateTime.now();
        Transaction withdrawal = makeTransaction(TransactionType.WITHDRAWAL, sourceAccount, destinationAccount, amount, now);
        Transaction deposit = makeTransaction(TransactionType.DEPOSIT, destinationAccount, sourceAccount, amount, now);

        transactionCrudPort.save(withdrawal);
        transactionCrudPort.save(deposit);

        return sourceAccount.getBalance();
    }

    private Transaction makeTransaction(TransactionType transactionType, Account sourceAccount, Account destinationAccount, int amount, LocalDateTime now){
        return Transaction.builder()
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .amount(amount)
                .transactionType(transactionType)
                .transactionTime(now)
                .build();
    }



}
