package buckpal.hexagonal.account.adapter.in.web;


import buckpal.hexagonal.account.domain.Account;
import buckpal.hexagonal.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
class AccountMapper {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public AccountCrudController.AccountDtoWithTransactions mapToDto(Account account){

        String number = account.getAccountNumber();
        int money = account.getMoney();

        List<Transaction> transactions = account.getTransactions();
        if(transactions.isEmpty())
            return new AccountCrudController.AccountDtoWithTransactions(number, money, new ArrayList<>());

        Map<String, List<TransactionByDate.TransactionDto>> map = new HashMap<>();

        for (Transaction transaction : transactions) {
            String transactionDate = transaction.getTransactionTime().format(dateFormatter); // key
            TransactionByDate.TransactionDto transactionDto = new TransactionByDate.TransactionDto(
                    transaction.getTransactionTime().format(timeFormatter),
                    transaction.getTransactionType().name(),
                    transaction.getCounterPartName(),
                    transaction.getAccountMoneyAfterTransaction()); //value

            if(!map.containsKey(transactionDate)){
                ArrayList<TransactionByDate.TransactionDto> arr = new ArrayList<>();
                arr.add(transactionDto);
                map.put(transactionDate, arr);
            }else
                map.get(transactionDate).add(transactionDto);

        }

        List<TransactionByDate> transactionByDates = new ArrayList<>();

        List<String> dates = new ArrayList(map.keySet());
        Collections.sort(dates, (a, b)-> a.compareTo(b) * -1); //내림 차순

        for (String date : dates) {
            List<TransactionByDate.TransactionDto> transactionDtos = map.get(date);
            Collections.sort(transactionDtos, (a, b)-> a.getTransactionTime().compareTo(b.getTransactionTime()) * -1); //내림 차순
            transactionByDates.add(new TransactionByDate(date, transactionDtos));
        }

        return new AccountCrudController.AccountDtoWithTransactions(number, money, transactionByDates);
    }


}
