package pl.javastart.task.phone;

import pl.javastart.task.contracts.*;

public class Phone {
    private final Contract contract;

    public Phone(Contract contract) {
        this.contract = contract;
    }

    public void sendSms() {
        if (contract.sendSms()) {
            System.out.println("\nSMS wysłany\n");
        } else {
            System.out.println("\nNie udało się wysłać SMSa\n");
        }
    }

    public void sendMms() {
        if (contract.sendMms()) {
            System.out.println("\nMMS wysłany\n");
        } else {
            System.out.println("\nNie udało się wysłać MMSa\n");
        }
    }

    public void call(int seconds) {
        int actualCallDuration = contract.call(seconds);
        int fullMinutes = actualCallDuration / 60;
        int remainingSeconds = actualCallDuration % 60;

        if (actualCallDuration == 0) {
            System.out.println("\nNie wykonano połączenia. Brak środków na koncie.\n");
        } else if (actualCallDuration == seconds) {
            System.out.printf("%nWykonano pełne połączenie o długości %d sekund (%d min %d s)%n%n",
                    seconds, fullMinutes, remainingSeconds);
        } else if (actualCallDuration > 0 && actualCallDuration < seconds) {
            System.out.printf("%nWykonano połączenie. Po upływie %d sekund (%d min %d s) musiało ono zostać przerwane" +
                    " ze względu na brak dostępności dalszych środków.%n%n",
                    actualCallDuration, fullMinutes, remainingSeconds);
        } else {
            System.out.println("\nCoś poszło nie tak.\n");
        }
    }

    public void printAccountState() {
        System.out.println(contract.getAccountStateInfo());
    }
}
