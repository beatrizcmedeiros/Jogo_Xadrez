package xadrez;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Tabuleiro;

public class PecaXadrez extends Peca{
	private Color color;

	public PecaXadrez(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}	
}//class PecaXadrez extends Peca
