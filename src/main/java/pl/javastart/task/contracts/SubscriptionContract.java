package pl.javastart.task.contracts;

public class SubscriptionContract extends Contract {
    private final double subscriptionCost;

    public SubscriptionContract(double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    @Override
    public boolean sendSms() {
        sentSmsNumber++;
        return true;
    }

    @Override
    public boolean sendMms() {
        sentMmsNumber++;
        return true;
    }

    @Override
    public int call(int seconds) {
        return seconds;
    }

    @Override
    public String getAccountStateInfo() {
        return String.format("%sRachunek abonamentowy: %.2f zł%n", super.getAccountStateInfo(), subscriptionCost);
    }
}
