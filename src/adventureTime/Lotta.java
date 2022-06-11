package adventureTime;

import java.util.Random;

public class Lotta {
	private Entita giocatore, mostro;

	public Lotta(Entita giocatore, Entita mostro) {
		this.giocatore = giocatore;
		this.mostro = mostro;
	}
	
	public Entita combatti() {
		boolean turnoGiocatore = true;
		while(!(giocatore.getVita()<=0 || mostro.getVita()<=0)) {
			if (turnoGiocatore) {
				mostro.setVita(mostro.getVita()-calcolcoDanno(giocatore, mostro));
				turnoGiocatore = false;
			}
			else {
				giocatore.setVita(giocatore.getVita()-calcolcoDanno(mostro, giocatore));
				turnoGiocatore = true;
			}
		}
		return mostro.getVita()<=0 ? giocatore : mostro;
	}
	
	private int calcolcoDanno(Entita attacante, Entita difensore) {
		Random rand = new Random();
		int potenza = attacante.oggAttivo != null && attacante.oggAttivo.getTipoOgg() == Oggetti.Tipo.Arma ? attacante.oggAttivo.getAttributo() : 1;
		double modificatore = rand.nextDouble(200)<15 ? 1.5 : 1;
		return (int) ((2*potenza*attacante.getAtk()/25/difensore.getDif()+2)*modificatore);
	}
}
