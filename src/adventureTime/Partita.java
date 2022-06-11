package adventureTime;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;


/**
 * classe per gestire la partita
 * @author apost
 *
 */
public class Partita {

	private Livello corrente;
	private Personaggio giocatore = new Personaggio();

	private static final String INVENTARIO = "Inventario";
	private static final String STATISTICHE = "Statistiche";
	private static final String ESCI = "Abbandona partita";
	private static final String[] VOCI_MENU_GIOCO = {INVENTARIO, STATISTICHE, ESCI};
	Menu<String> menuDiGioco = new Menu<String>("Menu di gioco:", VOCI_MENU_GIOCO);

	public void gioca () {
		giocatore.setNome(InputDati.leggiStringaNonVuota("Dai un nome al tuo personaggio: "));
		CampoDiGioco cdg = new CampoDiGioco();
		cdg.setCampoDaGioco(cdg.GeneraMappaGioco());
		this.corrente = cdg.getPrimo();
		boolean finePartita = false;
		do {
			corrente.stampa();
			boolean continua;
			char casellaCorrente = 0;
			do { // gestisce le azioni
				continua = false;
				switch(InputDati.leggiStringaNonVuota("Azione richiesta> ").toLowerCase()) {
				case "a":
					if(casellaCalpestabile(corrente.dintonrniGiocatore()[0]))
						casellaCorrente = corrente.muoviGiocatore(0);
					else {
						continua = true;
					}
					break;
				case "w":
					if(casellaCalpestabile(corrente.dintonrniGiocatore()[1]))
						casellaCorrente = corrente.muoviGiocatore(1);
					else {
						continua = true;
					}
					break;
				case "d":
					if(casellaCalpestabile(corrente.dintonrniGiocatore()[2]))
						casellaCorrente = corrente.muoviGiocatore(2);
					else {
						continua = true;
					}
					break;
				case "s":
					if(casellaCalpestabile(corrente.dintonrniGiocatore()[3]))
						casellaCorrente = corrente.muoviGiocatore(3);
					else {
						continua = true;
					}
					break;
				case "m":
					if(menuDiGioco())
						return;
					break;
				case "e":
					if (cassaVicina() != -1) {
						Oggetti oggOttenuto = MyUtil.daiOggetto();
						giocatore.aggiungiOgg(oggOttenuto);
						System.out.println("ottenuto: " + oggOttenuto.getNomeOgg());
						int[] posRelativa = new int[2];
						posRelativa[0] = cassaVicina() == 1 ? -1 : cassaVicina() == 3 ? 1 :0;
						posRelativa[1] = cassaVicina() == 0 ? -1 : cassaVicina() == 2 ? 1 :0;
						corrente.setPunto(corrente.posizioneGiocatore()[0]+posRelativa[0], corrente.posizioneGiocatore()[1]+posRelativa[1], '.');
					}
					break;
				default: System.out.println("Azione non valida");
				}
			} while (continua);
			//gestione eventi
			if(casellaCorrente == 't' || casellaCorrente == 'T') {
				for (Map.Entry<Livello, Map<Livello,Integer[]>> partenza : cdg.getCampoDaGioco().ottieniArchi().entrySet())
					if(partenza.getKey() == corrente)
						for (Map.Entry<Livello,Integer[]> controlla : cdg.getCampoDaGioco().archiDiNodo(corrente).entrySet())//partenza.getValue().entrySet())
							if(corrente.posizioneGiocatore()[0] == controlla.getValue()[0] && corrente.posizioneGiocatore()[1] == controlla.getValue()[1]) {
								corrente.setPunto(corrente.posizioneGiocatore()[0], corrente.posizioneGiocatore()[1], casellaCorrente);
								controlla.getKey().setPunto(cdg.getCampoDaGioco().ottieniArco(controlla.getKey(), corrente)[0],cdg.getCampoDaGioco().ottieniArco(controlla.getKey(),corrente)[1], 'O');
								for (int i=0; i<4; i++)
									if(casellaCalpestabile(controlla.getKey().dintonrniGiocatore()[i])) {
										int[] pos = controlla.getKey().posizioneGiocatore();
										controlla.getKey().muoviGiocatore(i);
										controlla.getKey().setPunto(pos[0], pos[1], casellaCorrente == 't' ? 'T' : 't');
										break;
									}
								corrente=controlla.getKey();
								break;
							}
			}
			if(casellaCorrente=='M'){
				Lotta lotta = new Lotta(giocatore, Mostro.generaMostro());
				if(lotta.combatti()==giocatore)
					System.out.println("hai sconfitto il mostro (PS: " + giocatore.getVita() +")");
				else {
					System.out.println("hai perso");
					finePartita=true;
				}
			}
			if (casellaCorrente=='K') {
				System.out.println("hai salvato la principessa, hai vinto!");
				finePartita=true;
			}
		}while (!finePartita);
	}

	/**
	 * metodo per controllare se una cassella è "calpestabile" dal giocatore
	 * @param verifica : valore della casella
	 * @return
	 */
	private boolean casellaCalpestabile (Character verifica) {
		return verifica != null && verifica !='#' && verifica != 'C';
	}

	/**
	 * metodo per controllare se i giocatore è vicino ad una cassa
	 * @return
	 */
	private int cassaVicina () {
		Character[] dintorni = corrente.dintonrniGiocatore();
		for (int i=0; i< dintorni.length; i++)
			if (dintorni[i] == 'C')
				return i;
		return -1;
	}
	
	/**
	 * menu di gioco
	 * @return
	 */
	private boolean menuDiGioco() {
		for(;;) {
			int scelta = menuDiGioco.scegli()-1;
			if (scelta == -1)
				return false;
			switch(VOCI_MENU_GIOCO[scelta]) {
			case INVENTARIO:
				Menu<Oggetti> inventarioGiocatore = new Menu<Oggetti>("inventario");
				Function<Oggetti,String> stempaNomeOggetti  = (x) -> x.getTipoOgg() == Oggetti.Tipo.Consumabile ? String.format("%s (%d)", x.getNomeOgg(),giocatore.getInventario().get(x)) : x.getNomeOgg();
				ArrayList<Oggetti> inventarioArray = new ArrayList<Oggetti>();
				for(Map.Entry<Oggetti, Integer> a : giocatore.getInventario().entrySet())
					inventarioArray.add(a.getKey());
				for (Oggetti a : inventarioArray)
					inventarioGiocatore.aggiungiVoce(a);
				for(;;) {
					int sceltaInventario = inventarioGiocatore.scegli() - 1;
					if (sceltaInventario == -1)
						break;
					if(inventarioArray.get(sceltaInventario).getTipoOgg() == Oggetti.Tipo.Consumabile) {
						giocatore.setVita(giocatore.getVita()+10);
						if (giocatore.getVita()>20)
							giocatore.setVita(20);
						System.out.println("usato pozione, vita ripristinata");
						giocatore.rimuoviOgg(inventarioArray.get(sceltaInventario));
					}
					else {
						giocatore.setOggAttivo(inventarioArray.get(sceltaInventario));
						break;
					}
				}
				break;
			case STATISTICHE:
				System.out.println("\nnome: " + giocatore.getNome() +
				//"\noggeto equipaggiato: " + giocatore.getOggAttivo() == null ? "nessuno" : giocatore.getOggAttivo().getNomeOgg() +
				"\nvita: " + giocatore.getVita() +
				"\nattaco: " + giocatore.getAtk()+
				"\ndifesa: " + giocatore.getDif() +"\n");
				break;
			case ESCI:
				return true;
			}
		}
	}
}
