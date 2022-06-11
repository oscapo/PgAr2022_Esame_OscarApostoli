package adventureTime;

import java.util.Random;

public class MyUtil {
	
	/**
	 * funzione per ottenre un oggetto da un cassa (non si necessita di salire su di una cassa, basta starci vicino)
	 * @return
	 */
	public static Oggetti daiOggetto () {
		Random rand = new Random();
		int scelta = rand.nextInt(100);
		Oggetti ritorno = new Oggetti();
		if (scelta<40) {
			int attributo = rand.nextInt(35,56);
			ritorno.setAttributo(attributo);
			ritorno.setTipoOgg(Oggetti.Tipo.Arma);
			ritorno.setNomeOgg("Arma (" + attributo + ")");
			return ritorno;
		}
		if(scelta < 75) {
			ritorno.setAttributo(5);
			ritorno.setTipoOgg(Oggetti.Tipo.Scudo);
			ritorno.setNomeOgg("Scudo");
			return ritorno;
		}
		ritorno.setTipoOgg(Oggetti.Tipo.Consumabile);
		ritorno.setNomeOgg("Pozione");
		return ritorno;
	}
}
