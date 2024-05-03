package buckpal.hexagonal;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.lottery.domain.LotteryConst;
import buckpal.hexagonal.member.application.port.in.MemberLoginUseCase;
import buckpal.hexagonal.member.application.service.dto.MemberLoginRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;
import buckpal.hexagonal.transaction.application.port.in.TransactionUseCase;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static buckpal.hexagonal.lottery.domain.LotteryConst.*;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final EntityManager em;
    private final TransactionUseCase transactionService;
    private final MemberLoginUseCase memberLoginUseCase;
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        Member lotteryManager = Member.createMember(new MemberCreateRequest("lotteryManager", "lottery", "1234", "1234"));
        Account lotteryManagerAccount = Account.createAccount(LOTTERY_MANAGER_ACCOUNT_NUMBER, lotteryManager, new AccountCreateRequest("우리", 0));

        em.persist(lotteryManager);
        em.persist(lotteryManagerAccount);

        Member m1 = Member.createMember(new MemberCreateRequest("jay", "loginId1", "1234", "1234"));
        Member m2 = Member.createMember(new MemberCreateRequest("park", "loginId2", "1234", "1234"));

        Account m1_account1 = Account.createAccount("0000-0000-0", m1, new AccountCreateRequest("우리", 10000));
        Account m1_account2 = Account.createAccount("0000-0000-1", m1, new AccountCreateRequest("국민", 1500));

        Account m2_account1 = Account.createAccount("0000-0000-2", m2, new AccountCreateRequest("우리", 0));
        Account m2_account2 = Account.createAccount("0000-0000-3", m2, new AccountCreateRequest("우리", 0));

        em.persist(m1);
        em.persist(m1_account1);
        em.persist(m1_account2);

        em.persist(m2);
        em.persist(m2_account1);
        em.persist(m2_account2);

        for(int i=0; i<100; i++){
            TransactionRequest transactionRequest = new TransactionRequest(
                    "0000-0000-0","0000-0000-2",
                    1,"transfer");

            transactionService.transferMoney(transactionRequest);
        }

        for(int i=0; i<50; i++){
            TransactionRequest transactionRequest = new TransactionRequest(
                    "0000-0000-2","0000-0000-1",
                    1,"transfer");

            transactionService.transferMoney(transactionRequest);
        }

        for(int i=0; i<10; i++){
            TransactionRequest transactionRequest = new TransactionRequest(
                    "0000-0000-2","0000-0000-0",
                    1,"transfer");

            transactionService.transferMoney(transactionRequest);
        }

    }
}
