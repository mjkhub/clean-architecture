package buckpal.hexagonal.transaction.application.service;


import buckpal.hexagonal.account.application.port.out.AccountCrudPort;
import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.member.application.port.out.MemberCrudPort;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.transaction.adapter.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.port.out.TransactionCrudPort;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import buckpal.hexagonal.transaction.domain.Transaction;
import buckpal.hexagonal.transaction.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class TransactionService implements TransactionUseCase {

    private final TransactionCrudPort transactionCrudPort;
    private final AccountCrudPort accountCrudPort;
    private final MemberCrudPort memberCrudPort;

    public void transferMoney(Long sourceMemberId, TransactionRequest tr){

        Account sourceAccount = accountCrudPort.findByAccountName(tr.getSourceAccountNumber());
        Account destinationAccount = accountCrudPort.findByAccountName(tr.getDestinationAccountNumber()); //fetch로 멤버를 가져와야함
        Member sourceMember = sourceAccount.getMember();
        Member destinationMember = destinationAccount.getMember();
        int transferMoney = tr.getMoney();

        // 돈을 보낸 입장 -> 송금 transaction 생성
        Transaction transferTransaction = new Transaction(tr.getSourceAccountNumber(), tr.getDestinationAccountNumber(), tr.getMoney(), TransactionType.TRANSFER, LocalDateTime.now(), sourceAccount, destinationAccount.getMember().getName(), sourceAccount.getMoney() - tr.getMoney());
        transactionCrudPort.save(transferTransaction);

        // 돈을 보낸 account 의 정보 변경
        // 1. 계좌의 금액  2. 멤버가 가진 돈
        accountCrudPort.updateAccountMoney(sourceMemberId, sourceAccount.getMoney() - transferMoney);
        memberCrudPort.updateTotalMoney(sourceMember.getId(), sourceMember.getTotalMoney().get() - transferMoney);

        // 돈을 받은 입장 -> 입금 transaction 생성
        Transaction depositTransaction = new Transaction(tr.getSourceAccountNumber(), tr.getDestinationAccountNumber(), tr.getMoney(), TransactionType.DEPOSIT, LocalDateTime.now(), destinationAccount, sourceAccount.getMember().getName(), destinationAccount.getMoney() + tr.getMoney());
        transactionCrudPort.save(depositTransaction);

        // 돈을 받은 account 정보 변경
        // 1. 계좌의 금액  2. 멤버의 가진 돈
        accountCrudPort.updateAccountMoney(destinationAccount.getId(), destinationAccount.getMoney() + transferMoney);
        memberCrudPort.updateTotalMoney(destinationMember.getId(), destinationMember.getTotalMoney().get() + transferMoney);

        //다 되고 나면.. Account State를 반환하도록
    }



}
