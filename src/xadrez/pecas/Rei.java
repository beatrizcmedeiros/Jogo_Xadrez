package xadrez.pecas;

import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "K"; //King
	}
}//class Rei extends PecaXadrez
