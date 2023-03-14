package tabuleirodejogo;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1)
			throw new ExcecaoDoTabuleiro("Erro ao criar o tabuleiro: e necessario que exista pelo menos uma linha e uma coluna.");
		this.colunas = colunas;
		this.linhas = linhas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if (!posicaoExistente(linha, coluna))
			throw new ExcecaoDoTabuleiro("Essa posicao nao existe no tabuleiro.");
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if (!posicaoExistente(posicao))
			throw new ExcecaoDoTabuleiro("Essa posicao nao existe no tabuleiro.");
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocaPeca(Peca peca, Posicao posicao) {
		if(possuiPeca(posicao))
			throw new ExcecaoDoTabuleiro("Ja existe uma peca na posicao (" + posicao + ").");
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	
	public boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean possuiPeca(Posicao posicao) {
		if (!posicaoExistente(posicao))
			throw new ExcecaoDoTabuleiro("Essa posicao nao existe no tabuleiro.");
		return peca(posicao) != null;
	}
	
}// class Tabuleiro
