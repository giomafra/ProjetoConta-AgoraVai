package conta.model;

import java.time.LocalDateTime;

public class TransacaoModel {
    private int id;
    private double valor;
    private String tipoTransacao; // "receita" ou "despesa"
    private LocalDateTime dataHoraTransacao;
    private int usuarioId;
    private int contaId;
    private int categoriaId;

    public TransacaoModel(double valor, String tipoTransacao, LocalDateTime dataHoraTransacao, int usuarioId, int contaId, int categoriaId) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataHoraTransacao = dataHoraTransacao;
        this.usuarioId = usuarioId;
        this.contaId = contaId;
        this.categoriaId = categoriaId;
    }

    public TransacaoModel(double valor, String tipoTransacao, LocalDateTime dataHoraTransacao) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataHoraTransacao = dataHoraTransacao;
    }


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getContaId() {
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TransacaoModel{" +
                "valor=" + valor +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", dataHoraTransacao=" + dataHoraTransacao +
                ", usuarioId=" + usuarioId +
                ", contaId=" + contaId +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
