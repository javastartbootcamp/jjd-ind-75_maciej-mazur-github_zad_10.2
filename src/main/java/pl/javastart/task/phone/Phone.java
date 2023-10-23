package pl.javastart.task.phone;

import pl.javastart.task.contracts.*;

public class Phone {
    private final Contract contract;
    private final ContractType contractType;
    private final AccountStatus accountStatus;

    public Phone(Contract contract) {
        this.contract = contract;
        this.contractType = contract.getContractType();

        accountStatus = switch (contract.getContractType()) {
            case SUBSCRIPTION ->
                    new SubscriptionAccountStatus(((SubscriptionContract) contract).getSubscriptionCost());
            case CARD ->
                    new CardContractAccountStatus(((CardPhoneContract) contract).getAccountBalance());
            case MIX -> {
                MixContract mixContract = (MixContract) contract;
                yield new MixContractAccountStatus(mixContract.getFreeFunds(), mixContract.getFreeSmsNumber(),
                        mixContract.getFreeMmsNumber(), mixContract.getFreeCallMinutes());
            }
        };
    }

    public void sendSms() {
        switch (contractType) {
            case SUBSCRIPTION -> sendSms((SubscriptionAccountStatus) accountStatus);
            case CARD -> sendSms((CardContractAccountStatus) accountStatus);
            case MIX -> sendSms((MixContractAccountStatus) accountStatus);
            default -> { }
        }
    }

    private void sendSms(MixContractAccountStatus accountStatus) {
        MixContract mixContract = (MixContract) contract;
        int freeSmsNumber = accountStatus.getFreeSmsNumber();

        if (freeSmsNumber > 0) {
            System.out.println("\nSMS wysłany\n");
            accountStatus.setFreeSmsNumber(freeSmsNumber - 1);
            accountStatus.setSmsSent(accountStatus.getSmsSent() + 1);
            return;
        }

        /*
        Poniżej rozpatrzenie przypadku, gdy wykończył się już pakiet SMS-ów i dalsze SMS-y pomniejszać będą kwotę środków wolnych
         */
        double smsCost = mixContract.getSmsCost();
        double freeFunds = accountStatus.getFreeFunds();

        if ((freeFunds - smsCost) >= 0) {
            System.out.println("\nSMS wysłany\n");
            accountStatus.setFreeFunds(freeFunds - smsCost);
            accountStatus.setSmsSent(accountStatus.getSmsSent() + 1);
        } else {
            System.out.println("\nNie udało się wysłać SMSa\n");
        }
    }

    private void sendSms(CardContractAccountStatus accountStatus) {
        CardPhoneContract cardPhoneContract = (CardPhoneContract) contract;
        double smsCost = cardPhoneContract.getSmsCost();
        double accountBalance = accountStatus.getAccountBalance();

        if ((accountBalance - smsCost) >= 0) {
            System.out.println("\nSMS wysłany\n");
            accountStatus.setAccountBalance(accountBalance - smsCost);
            accountStatus.setSmsSent(accountStatus.getSmsSent() + 1);
        } else {
            System.out.println("\nNie udało się wysłać SMSa\n");
        }
    }

    private void sendSms(SubscriptionAccountStatus accountStatus) {
        accountStatus.setSmsSent(accountStatus.getSmsSent() + 1);
        System.out.println("\nSMS wysłany\n");
    }

    public void call(int seconds) {
        switch (contractType) {
            case SUBSCRIPTION -> call((SubscriptionAccountStatus) accountStatus, seconds);
            case CARD -> call((CardContractAccountStatus) accountStatus, seconds);
            case MIX -> call((MixContractAccountStatus) accountStatus, seconds);
            default -> { }
        }
    }

    private void call(MixContractAccountStatus accountStatus, int seconds) {
        MixContract mixContract = (MixContract) contract;
        int freeCallSeconds = accountStatus.getFreeCallSeconds();
        double freeFunds = accountStatus.getFreeFunds();

        if (freeCallSeconds == 0 && freeFunds == 0) {
            System.out.println("\nBrak środków na koncie. Nie można wykonać połączenia.\n");
            return;
        }

        if (seconds <= freeCallSeconds) {
            printCallTimeDetails(seconds);
            accountStatus.setFreeCallSeconds(freeCallSeconds - seconds);
            accountStatus.setSecondsCalled(accountStatus.getSecondsCalled() + seconds);
            return;
        }

        int secondsToBeUsedFromFreeFunds = seconds - freeCallSeconds;
        accountStatus.setFreeCallSeconds(0);

        double callCostPerMinute = mixContract.getCallCost();
        double callCostPerSecond = callCostPerMinute / 60;
        int achievableCallSecondsFromFreeFunds = (int) (freeFunds / callCostPerSecond);
        int totalSecondsUsedForCall;

        if (secondsToBeUsedFromFreeFunds <= achievableCallSecondsFromFreeFunds) {
            totalSecondsUsedForCall = freeCallSeconds + secondsToBeUsedFromFreeFunds;
            printCallTimeDetails(totalSecondsUsedForCall);
            accountStatus.setFreeFunds(freeFunds - secondsToBeUsedFromFreeFunds * callCostPerSecond);
        } else {
            totalSecondsUsedForCall = freeCallSeconds + achievableCallSecondsFromFreeFunds;
            printCallTimeDetails(totalSecondsUsedForCall);
            System.out.println("Następnie przerwano połączenie w wyniku wykończenia środków pieniężnych na koncie.");
            accountStatus.setFreeFunds(0);
        }

        accountStatus.setSecondsCalled(accountStatus.getSecondsCalled() + totalSecondsUsedForCall);

    }
    
    private void call(SubscriptionAccountStatus accountStatus, int seconds) {
        printCallTimeDetails(seconds);
        accountStatus.setSecondsCalled(accountStatus.getSecondsCalled() + seconds);
    }

    private void call(CardContractAccountStatus accountStatus, int seconds) {
        double accountBalance = accountStatus.getAccountBalance();

        if (accountBalance == 0) {
            System.out.println();
            System.out.println("\nBrak środków na koncie. Nie można wykonać połączenia.\n");
            System.out.println();
            return;
        }

        CardPhoneContract cardPhoneContract = (CardPhoneContract) contract;
        double callCostPerMinute = cardPhoneContract.getCallCost();
        double callCostPerSecond = callCostPerMinute / 60;

        int achievableCallSeconds = (int) (accountBalance / callCostPerSecond);

        if (achievableCallSeconds < seconds) {
            accountStatus.setAccountBalance(0);
            accountStatus.setSecondsCalled(accountStatus.getSecondsCalled() + achievableCallSeconds);
            printCallTimeDetails(achievableCallSeconds);
            System.out.println("Następnie przerwano połączenie w wyniku wykończenia środków pieniężnych na koncie.");
        } else {
            accountStatus.setAccountBalance(accountBalance - seconds * callCostPerSecond);
            accountStatus.setSecondsCalled(accountStatus.getSecondsCalled() + seconds);
            printCallTimeDetails(seconds);
        }
    }

    private void printCallTimeDetails(int seconds) {
        int minutesNumber = seconds / 60;
        int remainingSecondsNumber = seconds % 60;
        System.out.printf("%nWykonano połączenie głosowe trwające %d sekund %s%n%n",
                seconds, minutesNumber > 0 ? "(" + minutesNumber + " min i " + remainingSecondsNumber + " s)" : "");
    }

    public void sendMms() {
        switch (contractType) {
            case SUBSCRIPTION -> sendMms((SubscriptionAccountStatus) accountStatus);
            case CARD -> sendMms((CardContractAccountStatus) accountStatus);
            case MIX -> sendMms((MixContractAccountStatus) accountStatus);
            default -> { }
        }
    }

    private void sendMms(MixContractAccountStatus accountStatus) {
        MixContract mixContract = (MixContract) contract;
        int freeMmsNumber = accountStatus.getFreeMmsNumber();

        if (freeMmsNumber > 0) {
            System.out.println("\nSMS wysłany\n");
            accountStatus.setFreeMmsNumber(freeMmsNumber - 1);
            accountStatus.setMmsSent(accountStatus.getMmsSent() + 1);
            return;
        }

        /*
        Poniżej rozpatrzenie przypadku, gdy wykończył się już pakiet MMS-ów i dalsze MMS-y pomniejszać będą kwotę środków wolnych
         */
        double mmsCost = mixContract.getMmsCost();
        double freeFunds = accountStatus.getFreeFunds();

        if ((freeFunds - mmsCost) >= 0) {
            System.out.println("\nMMS wysłany\n");
            accountStatus.setFreeFunds(freeFunds - mmsCost);
            accountStatus.setMmsSent(accountStatus.getMmsSent() + 1);
        } else {
            System.out.println("\nNie udało się wysłać MMSa\n");
        }
    }

    private void sendMms(CardContractAccountStatus accountStatus) {
        CardPhoneContract cardPhoneContract = (CardPhoneContract) contract;
        double mmsCost = cardPhoneContract.getMmsCost();
        double accountBalance = accountStatus.getAccountBalance();

        if ((accountBalance - mmsCost) >= 0) {
            System.out.println("\nMMS wysłany\n");
            accountStatus.setAccountBalance(accountBalance - mmsCost);
            accountStatus.setMmsSent(accountStatus.getMmsSent() + 1);
        } else {
            System.out.println("\nNie udało się wysłać MMSa\n");
        }
    }

    private void sendMms(SubscriptionAccountStatus accountStatus) {
        accountStatus.setMmsSent(accountStatus.getMmsSent() + 1);
        System.out.println("\nMMS wysłany\n");
    }

    public void printAccountState() {
        switch (contractType) {
            case SUBSCRIPTION -> printAccountState((SubscriptionAccountStatus) accountStatus);
            case CARD -> printAccountState((CardContractAccountStatus) accountStatus);
            case MIX -> printAccountState((MixContractAccountStatus) accountStatus);
            default -> { }
        }
    }

    private void printAccountState(MixContractAccountStatus accountStatus) {
        printConsumptionInfo(accountStatus);
        int fullMinutesLeft = accountStatus.getFreeCallSeconds() / 60;
        int remainingSeconds = accountStatus.getFreeCallSeconds() % 60;

        System.out.printf("Pozostałe SMS-y: %d%n" +
                "Pozostałe MMS-y: %d%n" +
                "Pozostałe sekundy: %d (%d min %d s)%n" +
                "Stan konta: %.2f zł%n",
                accountStatus.getFreeSmsNumber(), accountStatus.getFreeMmsNumber(), accountStatus.getFreeCallSeconds(),
                fullMinutesLeft, remainingSeconds, accountStatus.getFreeFunds());
    }

    private void printAccountState(CardContractAccountStatus accountStatus) {
        printConsumptionInfo(accountStatus);
        System.out.printf("Na koncie zostało %.2f zł%n", accountStatus.getAccountBalance());
    }

    private void printAccountState(SubscriptionAccountStatus accountStatus) {
        printConsumptionInfo(accountStatus);
        System.out.printf("Rachunek abonamentowy: %.2f zł%n", accountStatus.getSubscriptionCost());
    }

    private void printConsumptionInfo(AccountStatus accountStatus) {
        System.out.printf("=== STAN KONTA ===%n" +
                "Wysłanych SMSów: %d%n" +
                "Wysłanych MMSów: %d%n" +
                "Liczba sekund rozmowy: %d%n",
                accountStatus.getSmsSent(), accountStatus.getMmsSent(), accountStatus.getSecondsCalled());
    }
}
