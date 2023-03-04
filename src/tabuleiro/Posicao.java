package tabuleiro;

public class Posicao {
	private int coluna;
	private int linha;
	
	public Posicao(int coluna, int linha) {
		super();
		this.coluna = coluna;
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	@Override
	public String toString() {
		return String.format("%d , %d", coluna, linha);
	}
}//class Posicao
