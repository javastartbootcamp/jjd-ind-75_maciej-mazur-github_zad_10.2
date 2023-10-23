package pl.javastart.task.contracts;

public abstract class Contract {
    public static final int NO_FUNDS = 0;

    int sentSmsNumber;
    int sentMmsNumber;
    int calledSecondsNumber;

    public abstract boolean sendSms();

    public abstract boolean sendMms();

    public abstract int call(int seconds);

    public String getAccountStateInfo() {
        return String.format("=== STAN KONTA ===%n" +
                "Wysłanych SMSów: %d%n" +
                "Wysłanych MMSów: %d%n" +
                "Liczba sekund rozmowy: %d%n",
                sentSmsNumber, sentMmsNumber, calledSecondsNumber);
    }
}
