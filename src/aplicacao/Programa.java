package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while(!partidaXadrez.getCheckMate()) {
			try {
				UI.limpaTela();
				UI.printPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem  = UI.lePosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limpaTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lePosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.realizaMovimentoPeca(origem, destino);
							
				if(pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if(partidaXadrez.getPromocao() != null) {
					System.out.print("\nInforme uma peca para realizar a promocao (C/B/T/R): ");
					String tipo = sc.nextLine().toUpperCase();
					
					while(!tipo.equals("C") && !tipo.equals("B") && !tipo.equals("T") && !tipo.equals("R")) {
						System.out.print("\nValor invalido! Informe uma peca corretamente para realizar a promocao (C/B/T/R): ");
						tipo = sc.nextLine().toUpperCase();
					}
					
					partidaXadrez.subistituiPecaPromovida(tipo);
				}
				
			}catch (ExcecaoDoXadrez e){
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		UI.limpaTela();
		UI.printPartida(partidaXadrez, capturadas);
		
	}

}//class Programa
