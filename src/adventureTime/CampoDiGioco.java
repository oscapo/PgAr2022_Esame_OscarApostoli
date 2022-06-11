package adventureTime;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


/**
 * classe utlizzata per rappresentare il campo da gioco, tiene traccaia dei livelli e dei loro collegameni tramite u grafo
 * @author apost
 *
 */
public class CampoDiGioco {
	
	private Livello ultimo, primo; // ultimo è il livello finale dove sta la principessa, primo è il livello su cui inizia il giocatore
	
	private Grafo<Livello,Integer[]> campoDaGioco = new GrafoMappato<Livello,Integer[]>();
	
	public Livello getUltimo() {
		return ultimo;
	}

	public void setUltimo(Livello ultimo) {
		this.ultimo = ultimo;
	}

	public Livello getPrimo() {
		return primo;
	}

	public void setPrimo(Livello primo) {
		this.primo = primo;
	}

	public Grafo<Livello, Integer[]> getCampoDaGioco() {
		return campoDaGioco;
	}

	public void setCampoDaGioco(Grafo<Livello, Integer[]> campoDaGioco) {
		this.campoDaGioco = campoDaGioco;
	}

	/**
	 * funzione per generare la mappa di gioco
	 * @return
	 */
	public Grafo<Livello,Integer[]> GeneraMappaGioco () {
		Grafo<Livello,Integer[]> ritorno = new GrafoMappato<Livello,Integer[]>();
		ArrayList<Livello> nodi = new ArrayList<Livello>();
		Random rand = new Random();
		int nLivelli =  rand.nextInt(5,8);//scelta n. livelli
		for (int i=0; i<nLivelli; i++) {
			Livello liv = new Livello();
			liv.setMappa(Livello.generaLivello());
			nodi.add(liv);
		}
		ritorno.impostaNodi(nodi);
		ArrayList<Livello> aperti = new ArrayList<Livello>(nodi);
		Livello temp = aperti.get(0);
		aperti.remove(0);
		while (!aperti.isEmpty()) {//crea un percorso fra i vari livelli
			int next = rand.nextInt(aperti.size());
			ritorno.impostaArco(temp, aperti.get(next), new Integer[] {rand.nextInt(2)});
			temp = aperti.get(next);
			aperti.remove(temp);
		}
		this.ultimo = temp;
		aperti.addAll(nodi);
		aperti.remove(temp);
		int nCollegamti = rand.nextInt((int)(nLivelli/1.5));
		for (int i=0; i<nCollegamti; i++) { // aggiungie collegamneti secondari ai livelli
			Livello partenza = nodi.get(rand.nextInt(nodi.size())), arrivo = aperti.get(rand.nextInt(aperti.size()));
			while(partenza == arrivo)
				arrivo = aperti.get(rand.nextInt(aperti.size()));
			if(ritorno.ottieniArco(partenza, arrivo) == null)
				ritorno.impostaArco(partenza, arrivo, new Integer[] {rand.nextInt(2)});
			else
				i--;
		}
		for (Map.Entry<Livello,Map<Livello,Integer[]>> partenza : ritorno.ottieniArchi().entrySet()) { // rende gli spostamenti bidirezzionali e imposta i delle scale
			for (Map.Entry<Livello,Integer[]> arrivo : partenza.getValue().entrySet()) {
				if(ritorno.ottieniArco(partenza.getKey(),arrivo.getKey()).length==1) {
				ritorno.impostaArco(partenza.getKey(), arrivo.getKey(), partenza.getKey().impostaRand('T'));
				ritorno.impostaArco(arrivo.getKey(), partenza.getKey(), arrivo.getKey().impostaRand('t'));
				}
			}
		}
		int primo = rand.nextInt(aperti.size()-2);
		aperti.get(primo).impostaRand('O');
		this.primo = aperti.get(primo);
		ultimo.impostaRand('K');
		return ritorno;
	}
}
