package pl.javastart.task.contracts;

public class CardPhoneContract extends Contract {
    private final double accountBalance;
    private final double smsCost;
    private final double mmsCost;
    private final double callCost;

    public CardPhoneContract(double accountBalance, double smsCost, double mmsCost, double callCost) {
        super(ContractType.CARD);
        this.accountBalance = accountBalance;
        this.smsCost = smsCost;
        this.mmsCost = mmsCost;
        this.callCost = callCost;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public double getSmsCost() {
        return smsCost;
    }

    public double getMmsCost() {
        return mmsCost;
    }

    public double getCallCost() {
        return callCost;
    }
}
