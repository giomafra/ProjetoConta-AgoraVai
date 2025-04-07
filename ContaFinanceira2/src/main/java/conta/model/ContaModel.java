package conta.model;

public class ContaModel implements Rendimento {
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

    public String getNomeDoBanco() {
        return nomeDoBanco;
    }

    public Integer getNumeroDaConta() {
        return numeroDaConta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
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

    @Override
    public double calcularRendimento() {
        if (tipoConta.equalsIgnoreCase("poupança")) {
            double taxa = 0.005; // 0.5% ao mês
            return saldoInicial * taxa;
        }
        return 0.0;
    }
}
