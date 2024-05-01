package xadrez.pecas;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "P"; //Peão
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().possuiPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(p.getLinha() - 1, p.getColuna());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().possuiPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().possuiPeca(p2) && getContMovimento() == 0) 
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExistente(p) && possuiPecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExistente(p) && possuiPecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			//moviemento especial en passant peça branca
			if(posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExistente(esquerda) && possuiPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPeaoVulneravel())
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExistente(direita) && possuiPecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getPeaoVulneravel())
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
			}
		}else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().possuiPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(p.getLinha() - 1, p.getColuna());
			if(getTabuleiro().posicaoExistente(p) && !getTabuleiro().possuiPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().possuiPeca(p2) && getContMovimento() == 0) 
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExistente(p) &&  possuiPecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExistente(p) &&  possuiPecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;
			
			//moviemento especial en passant peça preta
			if(posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExistente(esquerda) && possuiPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPeaoVulneravel())
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExistente(direita) && possuiPecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getPeaoVulneravel())
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
			}
		}

		return mat;
	}

}//class Peao
