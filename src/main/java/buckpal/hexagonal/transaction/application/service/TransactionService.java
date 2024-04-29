package buckpal.hexagonal.transaction.application.service;


import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountState;
import buckpal.hexagonal.transaction.application.port.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import buckpal.hexagonal.transaction.domain.Transaction;
import buckpal.hexagonal.transaction.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final TransactionCrudPort transactionCrudPort;
    private final AccountCrudPort accountCrudPort;

    @Transactional
    public AccountState transferMoney(TransactionRequest tr){

        Account sourceAccount = accountCrudPort.findByAccountNumber(tr.getSourceAccountNumber());
        Account destinationAccount = accountCrudPort.findByAccountNumber(tr.getDestinationAccountNumber()); //fetch로 멤버를 가져와야함

        int money = tr.getMoney();

        AccountState accountState = sourceAccount.transferMoney(destinationAccount, money);

        // 돈을 보낸 입장 -> 송금 transaction 생성
        Transaction transferTransaction = new Transaction(tr.getSourceAccountNumber(), tr.getDestinationAccountNumber(), tr.getMoney(), TransactionType.TRANSFER, LocalDateTime.now(), sourceAccount, destinationAccount.getMember().getName(), sourceAccount.getMoney() - tr.getMoney());
        transactionCrudPort.save(transferTransaction);

        // 돈을 받은 입장 -> 입금 transaction 생성
        Transaction depositTransaction = new Transaction(tr.getSourceAccountNumber(), tr.getDestinationAccountNumber(), tr.getMoney(), TransactionType.DEPOSIT, LocalDateTime.now(), destinationAccount, sourceAccount.getMember().getName(), destinationAccount.getMoney() + tr.getMoney());
        transactionCrudPort.save(depositTransaction);

        return accountState;
    }



}
