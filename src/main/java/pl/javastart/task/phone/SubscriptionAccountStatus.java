package pl.javastart.task.phone;

public class SubscriptionAccountStatus extends AccountStatus {
    private final double subscriptionCost;

    public SubscriptionAccountStatus(double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public double getSubscriptionCost() {
        return subscriptionCost;
    }
}
