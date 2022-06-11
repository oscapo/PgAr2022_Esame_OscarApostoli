package adventureTime;

import java.util.Random;

/**
 * classe per rappresentare un livello di gioco
 * @author apost
 *
 */
public class Livello {
	private char[][] mappa;

	public char[][] getMappa() {
		return mappa;
	}

	public void setMappa(char[][] mappa) {
		this.mappa = mappa;
	}

	public void setPunto(int i, int j, char value) {
		this.mappa[i][j] = value;
	}
	
	public char getPunto(int i, int j) {
		return this.mappa[i][j];
	}
	
	public void stampa() {
		for (char[] riga : mappa) {
			for(char colonna : riga)
				System.out.print(String.format("%c ", colonna));
			System.out.println();
		}
	}

	/**
	 * metodo per generare randomicamente un livello
	 * @return
	 */
	public static char[][] generaLivello() {
		Random rand = new Random();
		int I = rand.nextInt(5, 16), J= rand.nextInt(10, 21); //scelta dimensioni livello
		char[][] mappa = new char[I][J];
		for (int i=0; i<I; i++) // creazione base livello
			for (int j=0; j<J; j++)
				if(i==0 || i==I-1 || j==0 || j==J-1)
					mappa[i][j] = '#';
				else
					mappa[i][j]='.';
		int nMostri = rand.nextInt(I*J/100 + 1);
		int nCasse = rand.nextInt(3);
		for(int i=0; i<nMostri; i++) { // aggiunta mosti
			int _i = rand.nextInt(1,I),_j = rand.nextInt(1,J);
			if(mappa[_i][_j]=='.')
				mappa[_i][_j] = 'M';
			else
				i--;
		}
		for(int i=0; i<nCasse; i++) { // aggiunta casse
			int _i = rand.nextInt(1,I),_j = rand.nextInt(1,J);
			if(mappa[_i][_j]=='.')
				mappa[_i][_j] = 'C';
			else
				i--;
		}
		return mappa;
	}
	
	/**
	 * metodo per impostare randomicamnte una casella libera del livello con il carattere designato
	 * @param carattereDaImpostare : carattere da implmentare
	 * @return
	 */
	public Integer[] impostaRand (char carattereDaImpostare) {
		Random rand = new Random();
		int i = rand.nextInt(1,mappa.length), j = rand.nextInt(1,mappa[0].length);
		while (mappa[i][j] != '.') {
			i = rand.nextInt(1,mappa.length);
			j = rand.nextInt(1,mappa[0].length);
		}
		mappa[i][j] = carattereDaImpostare;
		return new Integer[] {i, j};
	}
	
	/**
	 * metodo per ottenre le caselle atorno al giocatore
	 * @return
	 */
	public Character[] dintonrniGiocatore() {
		Character[] ritorno=new Character[4];
		int i = posizioneGiocatore()[0], j = posizioneGiocatore()[1];
		ritorno[0] = j-1>=0 ? mappa[i][j-1] : null;
		ritorno[1] = i-1>=0 ? mappa[i-1][j] : null;
		ritorno[2] = j+1<mappa[0].length ? mappa[i][j+1] : null;
		ritorno[3] = i+1<mappa.length ? mappa[i+1][j] : null;
		return ritorno;
	}
	
	/**
	 * metodo per ottener le coordinate del giocatore
	 * @return
	 */
	public int[] posizioneGiocatore() {
		for (int i=0; i<mappa.length; i++)
			for (int j=0; j<mappa[0].length; j++)
				if(mappa[i][j]=='O')
					return new int[] {i, j};
		return new int[] {-1};
	}
	
	/**
	 * metdodo per sopostare il giocatore
	 * @param direzione : dierzione in cui spostre il giocatore (0 A, 1 W, 2 D, 3 S)
	 * @return
	 */
	public char muoviGiocatore (int direzione) {
		int[] posG = posizioneGiocatore();
		char ritorno = 0;
		switch (direzione) {
		case 0:
			mappa[posG[0]][posG[1]] = '.';
			ritorno = mappa[posG[0]][posG[1]-1];
			mappa[posG[0]][posG[1]-1] = 'O';
			break;
		case 1:
			mappa[posG[0]][posG[1]] = '.';
			ritorno = mappa[posG[0]-1][posG[1]];
			mappa[posG[0]-1][posG[1]] = 'O';
			break;
		case 2:
			mappa[posG[0]][posG[1]] = '.';
			ritorno = mappa[posG[0]][posG[1]+1];
			mappa[posG[0]][posG[1]+1] = 'O';
			break;
		case 3:
			mappa[posG[0]][posG[1]] = '.';
			ritorno = mappa[posG[0]+1][posG[1]];
			mappa[posG[0]+1][posG[1]] = 'O';
			break;
		}
		return ritorno;
	}
}
