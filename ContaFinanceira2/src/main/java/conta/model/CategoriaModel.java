package conta.model;

public class CategoriaModel {
    private String tipoCategoria;
    private int usuarioId;
    private int id;


    public CategoriaModel(String tipoCategoria, int usuarioId) {
        this.tipoCategoria = tipoCategoria;
        this.usuarioId = usuarioId;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public CategoriaModel() {}


    @Override
    public String toString() {
        return "CategoriaModel{" +
                "tipoCategoria='" + tipoCategoria + '\'' +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
