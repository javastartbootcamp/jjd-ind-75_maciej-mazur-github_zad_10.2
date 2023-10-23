package pl.javastart.task.phone;

abstract class AccountStatus {
    private int smsSent = 0;
    private int mmsSent = 0;
    private int secondsCalled = 0;

    int getSmsSent() {
        return smsSent;
    }

    void setSmsSent(int smsSent) {
        this.smsSent = smsSent;
    }

    int getMmsSent() {
        return mmsSent;
    }

    void setMmsSent(int mmsSent) {
        this.mmsSent = mmsSent;
    }

    int getSecondsCalled() {
        return secondsCalled;
    }

    void setSecondsCalled(int secondsCalled) {
        this.secondsCalled = secondsCalled;
    }
}
