package buckpal.hexagonal.account.domain;

public enum BankName {

    KOOKMIN("국민"), WOORI("우리");

    private String bankName;

    BankName(String bankName) {
        this.bankName = bankName;
    }

    public static BankName getBankType(String bankName){
        for (BankName value : BankName.values()) {
            if(value.bankName.equals(bankName)){
                return value;
            }
        }
        throw new IllegalArgumentException("Wrong bankName");
    }
}
