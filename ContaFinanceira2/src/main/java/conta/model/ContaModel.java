package conta.model;

public class ContaModel implements Rendimento {
    private int id;
    private String tipoConta;
    private String banco;
    private int numeroConta;
    private double saldo;

    public ContaModel(String tipoConta, String banco, int numeroConta, double saldo) {
        this.tipoConta = tipoConta;
        this.banco = banco;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public ContaModel(int id, String tipoConta, String banco, int numeroConta, double saldo) {
        this.id = id;
        this.tipoConta = tipoConta;
        this.banco = banco;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTipoConta() {
        return tipoConta;
    }


    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public String toString() {
        return "ContaModel{" +
                "id=" + id +
                ", tipoConta='" + tipoConta + '\'' +
                ", banco='" + banco + '\'' +
                ", numeroConta=" + numeroConta +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public double calcularRendimento() {
        if (tipoConta.equalsIgnoreCase("poupança")) {
            double taxa = 0.005; // 0.5% ao mês
            return saldo * taxa;
        }
        return 0.0;
    }
}
