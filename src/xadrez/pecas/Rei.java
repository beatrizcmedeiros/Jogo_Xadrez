package xadrez.pecas;

<<<<<<< HEAD
import tabuleirodejogo.Posicao;
=======
>>>>>>> f41f255cae728d6cec6a3feb09d1962d12616d91
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
<<<<<<< HEAD
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();			
	}
=======
>>>>>>> f41f255cae728d6cec6a3feb09d1962d12616d91

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
<<<<<<< HEAD
		
		Posicao p = new Posicao(0, 0);
		
		//acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
				
=======
>>>>>>> f41f255cae728d6cec6a3feb09d1962d12616d91
		return mat;
	}
}//class Rei extends PecaXadrez
