package br.com.datasol.vendasmcel.auxilio;

/**
	Cada linha aqui é igual a uma linha retornada pelo select chamado
*/
public class TabelaLinha{

    private TabelaColuna linha = new TabelaColuna();


    /**
     * Conteúdo lido do DB
     */
    public String getAtributo(int coluna) {
        return this.getLinha().get(coluna);
    }

    /**
     * Dado lido no DB ou para ser enviado a ele
     */
    public TabelaColuna getLinha() {
        return this.linha;
    }

    /**
     * Ajusto o valor do DB
     *
     */
    public void setAtributo(int coluna, String valor) {
        this.getLinha().set(coluna, valor);
    }

}