package xadrez;

import tabuleirodejogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		
		return matriz;
	}
	
	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).convertePosicao());
	}
	
	private void configuracaoInicial() {		
		colocaNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocaNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}//class PartidaXadrez 
