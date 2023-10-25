package pl.javastart.task.contracts;

public class MixContract extends CardContract {
    private int smsPackage;
    private int mmsPackage;
    private int secondsPackage;

    public MixContract(double freeFunds, int smsPackage, int mmsPackage, int freeCallMinutes,
                       double smsCost, double mmsCost, double callCostPerMinute) {
        super(freeFunds, smsCost, mmsCost, callCostPerMinute);
        this.smsPackage = smsPackage;
        this.mmsPackage = mmsPackage;
        this.secondsPackage = freeCallMinutes * 60;
    }

    @Override
    public boolean sendSms() {
        if (smsPackage > 0) {
            smsPackage--;
            sentSmsNumber++;
            return true;
        } else {
            return super.sendSms();
        }
    }

    @Override
    public boolean sendMms() {
        if (mmsPackage > 0) {
            mmsPackage--;
            sentMmsNumber++;
            return true;
        } else {
            return super.sendMms();
        }
    }

    @Override
    public int call(int seconds) {
        if (secondsPackage == 0 && accountBalance == 0) {
            return NO_FUNDS;
        }

        if (seconds <= secondsPackage) {
            return usePackageSecondsOnly(seconds);
        } else {
            return usePackageSecondsAndFreeFunds(seconds);
        }
    }

    private int usePackageSecondsOnly(int seconds) {
        secondsPackage -= seconds;
        calledSecondsNumber += seconds;
        return seconds;
    }

    private int usePackageSecondsAndFreeFunds(int seconds) {
        int totalCallDuration = secondsPackage;
        calledSecondsNumber += totalCallDuration;
        secondsPackage = 0;
        return totalCallDuration + super.call(seconds - totalCallDuration);
    }

    @Override
    public String getAccountStateInfo() {
        int fullMinutes = secondsPackage / 60;
        int remainingSeconds = secondsPackage % 60;

        return String.format("%s=========================%n" +
                        "Pozostałe darmowe SMS-y: %d%n" +
                "Pozostałe darmowe MMS-y: %d%n" +
                "Pozostałe darmowe sekundy: %d (%d min %d s)%n",
                super.getAccountStateInfo(), smsPackage, mmsPackage,
                secondsPackage, fullMinutes, remainingSeconds);
    }
}
