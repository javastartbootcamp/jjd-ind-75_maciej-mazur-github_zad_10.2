package pl.javastart.task.contracts;

public class CardContract extends Contract {
    protected final double smsCost;
    protected final double mmsCost;
    protected final double callCostPerSecond;
    protected double accountBalance;

    public CardContract(double accountBalance, double smsCost, double mmsCost, double callCostPerMinute) {
        this.accountBalance = accountBalance;
        this.smsCost = smsCost;
        this.mmsCost = mmsCost;
        this.callCostPerSecond = callCostPerMinute / 60;
    }

    @Override
    public boolean sendSms() {
        if (smsCost <= accountBalance) {
            accountBalance -= smsCost;
            sentSmsNumber++;
            return true;
        }

        return false;
    }

    @Override
    public boolean sendMms() {
        if (mmsCost <= accountBalance) {
            accountBalance -= mmsCost;
            sentMmsNumber++;
            return true;
        }

        return false;
    }

    @Override
    public int call(int seconds) {
        if (accountBalance == 0) {
            return NO_FUNDS;
        }

        int achievableCallDuration = (int) (accountBalance / callCostPerSecond);

        if (seconds <= achievableCallDuration) {
            accountBalance -= callCostPerSecond * seconds;
            calledSecondsNumber += seconds;
            return seconds;
        } else {
            accountBalance = 0;
            calledSecondsNumber += achievableCallDuration;
            return achievableCallDuration;
        }
    }

    @Override
    public String getAccountStateInfo() {
        return String.format("%sNa koncie zostało %.2f zł ", super.getAccountStateInfo(), accountBalance);
    }
}
