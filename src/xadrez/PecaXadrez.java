package xadrez;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}	
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.convertePosicaoParaXadrez(posicao);
	}
	
	protected boolean possuiPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}//class PecaXadrez extends Peca
