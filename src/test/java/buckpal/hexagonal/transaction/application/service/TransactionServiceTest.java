package buckpal.hexagonal.transaction.application.service;

import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.account.domain.dto.AccountCreateRequest;
import buckpal.hexagonal.member.domain.Member;
import buckpal.hexagonal.member.domain.dto.MemberCreateRequest;
import buckpal.hexagonal.transaction.application.service.dto.TransactionRequest;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {


    @Autowired
    TransactionService transactionService;

    @Autowired
    EntityManager em;

    @BeforeEach
    private void init(){
        Member m1 = Member.createMember(new MemberCreateRequest("jay", "loginId1", "1234", "1234"));
        Member m2 = Member.createMember(new MemberCreateRequest("park", "loginId2", "1234", "1234"));

        Account account1 = Account.createAccount("0000-0000-0", m1, new AccountCreateRequest("우리", 10000));
        Account account2 = Account.createAccount("0000-0000-1", m2, new AccountCreateRequest("우리", 0));

        em.persist(m1);
        em.persist(m2);
        em.persist(account1);
        em.persist(account2);
    }


    @Transactional
    @Test
    void 계좌이체_정상() {
        TransactionRequest transactionRequest = new TransactionRequest(
                "0000-0000-0","0000-0000-1",
                1000,"transfer");

        transactionService.transferMoney(transactionRequest);


        em.flush();
        em.clear();

        Account srcAccount = em.find(Account.class, 1L);
        Account destAccount = em.find(Account.class, 2L);

        //src account test
        assertThat(srcAccount.getMoney()).isEqualTo(9000);
        assertThat(srcAccount.getMember().getTotalMoney().get()).isEqualTo(9000);

        //det account test
        assertThat(destAccount.getMoney()).isEqualTo(1000);
        assertThat(destAccount.getMember().getTotalMoney().get()).isEqualTo(1000);

    }

    @Transactional
    @Test
    public void 계좌이체_예외() throws Exception{
        //Given
        TransactionRequest transactionRequest = new TransactionRequest(
                "0000-0000-0","0000-0000-1",
                11000,"transfer");

        //When & Then

        Account srcAccount = em.find(Account.class, 1);
        Account destAccount = em.find(Account.class, 2);


        assertThatThrownBy( () -> transactionService.transferMoney(transactionRequest) )
                .isInstanceOf(IllegalArgumentException.class);

        //src account test
        assertThat(srcAccount.getMoney()).isEqualTo(10000);
        assertThat(srcAccount.getMember().getTotalMoney().get()).isEqualTo(10000);

        //det account test
        assertThat(destAccount.getMoney()).isEqualTo(0);
        assertThat(destAccount.getMember().getTotalMoney().get()).isEqualTo(0);

    }

}