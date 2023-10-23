package pl.javastart.task.phone;

class MixContractAccountStatus extends AccountStatus {
    private double freeFunds;
    private int freeSmsNumber;
    private int freeMmsNumber;
    private int freeCallSeconds;

    MixContractAccountStatus(double freeFunds, int freeSmsNumber, int freeMmsNumber, int freeCallMinutes) {
        this.freeFunds = freeFunds;
        this.freeSmsNumber = freeSmsNumber;
        this.freeMmsNumber = freeMmsNumber;
        this.freeCallSeconds = freeCallMinutes * 60;
    }

    double getFreeFunds() {
        return freeFunds;
    }

    int getFreeSmsNumber() {
        return freeSmsNumber;
    }

    int getFreeMmsNumber() {
        return freeMmsNumber;
    }

    int getFreeCallSeconds() {
        return freeCallSeconds;
    }

    void setFreeFunds(double freeFunds) {
        this.freeFunds = freeFunds;
    }

    void setFreeSmsNumber(int freeSmsNumber) {
        this.freeSmsNumber = freeSmsNumber;
    }

    void setFreeMmsNumber(int freeMmsNumber) {
        this.freeMmsNumber = freeMmsNumber;
    }

    void setFreeCallSeconds(int freeCallSeconds) {
        this.freeCallSeconds = freeCallSeconds;
    }
}
