package pl.javastart.task.contracts;

public class SubscriptionContract extends Contract {
    private final double subscriptionCost;

    public SubscriptionContract(double subscriptionCost) {
        super(ContractType.SUBSCRIPTION);
        this.subscriptionCost = subscriptionCost;
    }

    public double getSubscriptionCost() {
        return subscriptionCost;
    }
}
