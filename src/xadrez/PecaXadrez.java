package xadrez;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	private Cor cor;
	private int contMovimento;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContMovimento() {
		return contMovimento;
	}

	public void incrementaContMovimento(){
		contMovimento++;
	}
	
	public void decrementaContMovimento(){
		contMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.convertePosicaoParaXadrez(posicao);
	}
	
	protected boolean possuiPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}//class PecaXadrez extends Peca
