package pl.javastart.task.contracts;

public class MixContract extends CardContract {
    private int freeSmsNumber;
    private int freeMmsNumber;
    private int freeCallSeconds;

    public MixContract(double freeFunds, int freeSmsNumber, int freeMmsNumber, int freeCallMinutes,
                       double smsCost, double mmsCost, double callCostPerMinute) {
        super(freeFunds, smsCost, mmsCost, callCostPerMinute);
        this.freeSmsNumber = freeSmsNumber;
        this.freeMmsNumber = freeMmsNumber;
        this.freeCallSeconds = freeCallMinutes * 60;
    }

    @Override
    public boolean sendSms() {
        if (freeSmsNumber == 0 && smsCost > accountBalance) {
            return false;
        }
        if (freeSmsNumber > 0) {
            freeSmsNumber--;
        } else {
            accountBalance -= smsCost;
        }

        return true;
    }

    @Override
    public boolean sendMms() {
        if (freeMmsNumber == 0 && mmsCost > accountBalance) {
            return false;
        }
        if (freeMmsNumber > 0) {
            freeMmsNumber--;
        } else {
            accountBalance -= mmsCost;
        }

        return true;
    }

    @Override
    public int call(int seconds) {
        if (freeCallSeconds == 0 && accountBalance == 0) {
            return NO_FUNDS;
        }

        if (seconds <= freeCallSeconds) {
            freeCallSeconds -= seconds;
            calledSecondsNumber += seconds;
            return seconds;
        }

        /*
        Poniżej przypadek, gdy połączenie musi zostać pokryte częściowo z darmowych minut i częściowo z wolnych środków
         */

        int secondsNeededFromFreeFunds = seconds - freeCallSeconds;
        int achievableFreeFundsSeconds = (int) (accountBalance / callCostPerSecond);
        int totalCallDuration = freeCallSeconds;
        freeCallSeconds = 0;

        if (secondsNeededFromFreeFunds <= achievableFreeFundsSeconds) {
            totalCallDuration += secondsNeededFromFreeFunds;
            accountBalance -= secondsNeededFromFreeFunds * callCostPerSecond;
        } else {
            totalCallDuration += achievableFreeFundsSeconds;
            accountBalance -= achievableFreeFundsSeconds + callCostPerSecond;
        }

        return calledSecondsNumber += totalCallDuration;
    }

    @Override
    public String getAccountStateInfo() {
        int fullMinutes = freeCallSeconds / 60;
        int remainingSeconds = freeCallSeconds % 60;

        return String.format("%s=========================%n" +
                        "Pozostałe darmowe SMS-y: %d%n" +
                "Pozostałe darmowe MMS-y: %d%n" +
                "Pozostałe darmowe sekundy: %d (%d min %d s)%n",
                super.getAccountStateInfo(), freeSmsNumber, freeMmsNumber,
                freeCallSeconds, fullMinutes, remainingSeconds);
    }
}
