package xadrez.pecas;

import tabuleirodejogo.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "K"; //King
	}
}//class Rei extends PecaXadrez
