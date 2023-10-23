package pl.javastart.task.contracts;

public abstract class Contract {
    private final ContractType contractType;

    public Contract(ContractType contractType) {
        this.contractType = contractType;
    }

    public ContractType getContractType() {
        return contractType;
    }
}
