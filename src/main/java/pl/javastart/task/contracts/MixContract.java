package pl.javastart.task.contracts;

public class MixContract extends Contract {
    /*
    W pierwszym polu użyłem kompozycji zamiast uczynić z MixContract pochodną CardPhoneContract ze względu na wykorzystaną
    przeze mnie strategię wpisywania na sztywno typu kontraktu (ContractType). Gdybym użył wspomnianego dziedziczenia, to nie
    miałbym potem możliwości zmienić wartości pola contractType na ContractType.MIX
     */
    private final CardPhoneContract cardContractPart;
    private final double freeFunds;
    private final int freeSmsNumber;
    private final int freeMmsNumber;
    private final int freeCallMinutes;

    public MixContract(double freeFunds, int freeSmsNumber, int freeMmsNumber, int freeCallMinutes,
                       double smsCost, double mmsCost, double callCost) {
        super(ContractType.MIX);
        this.freeFunds = freeFunds;
        this.freeSmsNumber = freeSmsNumber;
        this.freeMmsNumber = freeMmsNumber;
        this.freeCallMinutes = freeCallMinutes;
        this.cardContractPart = new CardPhoneContract(freeFunds, smsCost, mmsCost, callCost);
    }

    public double getFreeFunds() {
        return freeFunds;
    }

    public int getFreeSmsNumber() {
        return freeSmsNumber;
    }

    public int getFreeMmsNumber() {
        return freeMmsNumber;
    }

    public int getFreeCallMinutes() {
        return freeCallMinutes;
    }

    public double getSmsCost() {
        return cardContractPart.getSmsCost();
    }

    public double getMmsCost() {
        return cardContractPart.getMmsCost();
    }

    public double getCallCost() {
        return cardContractPart.getCallCost();
    }
}
