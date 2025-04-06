package conta.model;

import java.time.LocalDateTime;

public class TransacaoModel {
    private Double valor;
    private String tipoTransacao;
    private LocalDateTime dataHoraTransacao;

    public TransacaoModel(Double valor, String tipoTransacao, LocalDateTime dataHoraTransacao) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDateTime getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(LocalDateTime dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    @Override
    public String toString() {
        return "TransacaoModel{" +
                "valor=" + valor +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", dataHoraTransacao=" + dataHoraTransacao +
                '}';
    }
}
