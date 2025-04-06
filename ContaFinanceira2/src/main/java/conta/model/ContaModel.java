package conta.model;

public class ContaModel {
    private String tipoConta;
    private String nomeDoBanco;
    private Integer numeroDaConta;
    private Double saldoInicial;

    public ContaModel(String tipoConta, String nomeDoBanco, Integer numeroDaConta, Double saldoInicial) {
        this.tipoConta = tipoConta;
        this.nomeDoBanco = nomeDoBanco;
        this.numeroDaConta = numeroDaConta;
        this.saldoInicial = saldoInicial;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNomeDoBanco() {
        return nomeDoBanco;
    }

    public void setNomeDoBanco(String nomeDoBanco) {
        this.nomeDoBanco = nomeDoBanco;
    }

    public Integer getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Integer numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    @Override
    public String toString() {
        return "ContaModel{" +
                "tipoConta='" + tipoConta + '\'' +
                ", nomeDoBanco='" + nomeDoBanco + '\'' +
                ", numeroDaConta=" + numeroDaConta +
                ", saldoInicial=" + saldoInicial +
                '}';
    }
}