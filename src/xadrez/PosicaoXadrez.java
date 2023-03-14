package xadrez;

import tabuleirodejogo.Posicao;

public class PosicaoXadrez {
	private int linha;
	private char coluna;
	
	public PosicaoXadrez(char coluna, int linha) {
		if(linha < 1 || linha > 8 || coluna < 'a' || coluna > 'h')
			throw new ExcecaoDoXadrez("Erro ao definir a posicao do xadrez. Os valores validos sao de a1 a h8."); 
		this.linha = linha;
		this.coluna = coluna;
	}

	public int getLinha() {
		return linha;
	}

	public char getColuna() {
		return coluna;
	}
	
	protected Posicao convertePosicao() {
		return new Posicao(8-linha, coluna - 'a');
	}
	
	
	protected static PosicaoXadrez convertePosicaoParaXadrez(Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return String.format("%c%d", coluna, linha);
	}
}// class PosicaoXadrez
