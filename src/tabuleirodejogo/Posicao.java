package tabuleirodejogo;

public class Posicao {
	private int linha;
	private int coluna;
	
	public Posicao(int coluna, int linha) {
		this.coluna = coluna;
		this.linha = linha;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}
	
	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	@Override
	public String toString() {
		return String.format("%d , %d", coluna, linha);
	}
}//class Posicao
