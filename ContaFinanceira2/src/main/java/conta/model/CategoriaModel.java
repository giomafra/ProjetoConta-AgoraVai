package conta.model;

    public class CategoriaModel {
        private String tipoCategoria;

        public CategoriaModel(String tipoCategoria) {
            this.tipoCategoria = tipoCategoria;
        }

        public String getTipoCategoria() {
            return tipoCategoria;
        }

        public void setTipoCategoria(String tipoCategoria) {
            this.tipoCategoria = tipoCategoria;
        }

        @Override
        public String toString() {
            return "CategoriaModel{" +
                    "tipoCategoria='" + tipoCategoria + '\'' +
                    '}';
        }
    }


