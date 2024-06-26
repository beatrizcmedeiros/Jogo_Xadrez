package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleirodejogo.Peca;
import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez peaoVulneravel;
	private PecaXadrez promocao;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public boolean getCheck() {
		return check;
	}
	
	public PecaXadrez getPeaoVulneravel() {
		return peaoVulneravel;
	}

	public PecaXadrez getPromocao() {
		return promocao;
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
		
		if(testandoCheck(jogadorAtual)) {
			desfazMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoDoXadrez("Voce nao pode se colocar em check!");
		}
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//movimento especial promoção
		promocao = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = subistituiPecaPromovida("R");
			}
		}
		
		check = (testandoCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testandoCheckMate(oponente(jogadorAtual)))
			checkMate = true;
		else
			proximoTurno();
		
		//moviemento especial en passant
		if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2))
			peaoVulneravel = pecaMovida;
		else
			peaoVulneravel = null;
				
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez subistituiPecaPromovida(String tipo) {
		if(promocao == null)
			throw new IllegalStateException("Nao existe peca para executar a promocao.");
		if(!tipo.equals("C") && !tipo.equals("B") && !tipo.equals("T") && !tipo.equals("R")) 
			return promocao;
		
		Posicao pos = promocao.getPosicaoXadrez().convertePosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.colocaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if(tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if(tipo.equals("R"))
			return new Rainha(tabuleiro, cor);
		
		return new Torre(tabuleiro, cor);
	}

	private Peca realizaMovimento(Posicao origem, Posicao destino) {
		PecaXadrez peca = (PecaXadrez)tabuleiro.removePeca(origem);
		peca.incrementaContMovimento();
		
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		
		tabuleiro.colocaPeca(peca, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// movimento especial roque pequeno, lado do rei
		if(peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);	
			torre.incrementaContMovimento();
		}
		
		// movimento especial roque grande, lado da rainha
		if(peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, destinoT);		
			torre.incrementaContMovimento();
		}
		
		//movimento eespecial en passant
		if(peca instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if(peca.getCor() == Cor.BRANCO)
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				else
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}
	
	private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez peca = (PecaXadrez)tabuleiro.removePeca(destino);
		peca.decrementaContMovimento();
		
		tabuleiro.colocaPeca(peca, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		
		// movimento especial roque pequeno, lado do rei
		if(peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);			
			torre.decrementaContMovimento();
		}
		
		// movimento especial roque grande, lado da rainha
		if(peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
			tabuleiro.colocaPeca(torre, origemT);	
			torre.decrementaContMovimento();
		}
		
		//movimento eespecial en passant
		if(peca instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == peaoVulneravel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if(peca.getCor() == Cor.BRANCO)
					posicaoPeao = new Posicao(3, destino.getColuna());
				else
					posicaoPeao = new Posicao(4, destino.getColuna());
				tabuleiro.colocaPeca(peao, posicaoPeao);
			}
		}
	}

	private void validaPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.possuiPeca(posicao))
			throw new ExcecaoDoXadrez("\nNao existe nenhuma peca na posicao de origem.");
		
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor())
			throw new ExcecaoDoXadrez("\nA peca escolhida não e sua.");
		
		if(!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel())
			throw new ExcecaoDoXadrez("\nNao existe nenhuma forma possivel de mover a peca escolhida.");
	}

	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino))
			throw new ExcecaoDoXadrez("A peca escolhida nao pode ser movida para o destino escolhido.");
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor ).collect(Collectors.toList());
		for(Peca p : lista) {
			if(p instanceof Rei)
				return (PecaXadrez)p;
		}
		throw new IllegalStateException("Não existe o " + cor + " rei no tabuleiro.");
	}
	
	private boolean testandoCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().convertePosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor) ).collect(Collectors.toList());
		
		for( Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()])
				return true;
		}
		return false;
	}
	
	private boolean testandoCheckMate(Cor cor) {
		if(!testandoCheck(cor))
			return false;
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor ).collect(Collectors.toList());
		
		for(Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for(int i = 0; i < tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().convertePosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = realizaMovimento(origem, destino);
						boolean testandoCheck = testandoCheck(cor);
						desfazMovimento(origem, destino, pecaCapturada);
						if(!testandoCheck)
							return false;
					}
				}
			}
			
		}
		return true;
	}
	
	
	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).convertePosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial() {		
		colocaNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));	
        colocaNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocaNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        
        colocaNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocaNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        colocaNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocaNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocaNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocaNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO)); 
        colocaNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocaNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        
        colocaNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocaNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}
}//class PartidaXadrez 
