package buckpal.hexagonal.transaction.domain;

public enum TransactionType {

    TRANSFER("transfer"), DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");


    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public static TransactionType getTransactionType(String type){

        for (TransactionType value : TransactionType.values()) {
            if(value.type.equals("type"))
                return value;
        }
        throw new IllegalArgumentException("Wrong type name");

    }
}
