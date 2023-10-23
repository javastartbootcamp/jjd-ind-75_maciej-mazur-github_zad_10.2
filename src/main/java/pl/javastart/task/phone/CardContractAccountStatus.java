package pl.javastart.task.phone;

class CardContractAccountStatus extends AccountStatus {
    private double accountBalance;

    CardContractAccountStatus(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
