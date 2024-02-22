package xadrez;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Posicao;
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.convertePosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez realizaMovimentoPeca(PosicaoXadrez posicao_origem, PosicaoXadrez posicao_destino) {
		Posicao origem = posicao_origem.convertePosicao();
		Posicao destino = posicao_destino.convertePosicao();
		
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		
		Peca pecaCapturada = realizaMovimento(origem, destino);
		
		return (PecaXadrez)pecaCapturada;
	}

	private Peca realizaMovimento(Posicao origem, Posicao destino) {
		Peca peca = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		
		tabuleiro.colocaPeca(peca, destino);

		return pecaCapturada;
	}

	private void validaPosicaoOrigem(Posicao origem) {
		if(!tabuleiro.possuiPeca(origem))
			throw new ExcecaoDoXadrez("\nNao existe nenhuma peca na posicao de origem.");
		if(!tabuleiro.peca(origem).existeAlgumMovimentoPossivel())
			throw new ExcecaoDoXadrez("\nNao existe nenhuma forma possivel de mover a peca escolhida.");
	}

	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino))
			throw new ExcecaoDoXadrez("A peca escolhida nao pode ser movida para o destino escolhido.");
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
