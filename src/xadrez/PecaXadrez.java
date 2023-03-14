package xadrez;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Tabuleiro;

public class PecaXadrez extends Peca{
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}	
}//class PecaXadrez extends Peca
