package adventureTime;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Menu<T> {
	
	private int nVoci=0;
	private LinkedList<T> voci = new LinkedList<T>();
	private ArrayList<Integer> saltati = new ArrayList<Integer>();
	private String titolo = null;
	private String conclusione = "Esci";
	private String formatoVoci = "%s  %s";
	private String formatoConclusione = "\n%s  %s";
	private String messaggioNessunaVoce = "NESSUNA VOCE";
	private Function<T,String>[] metodiStampa = null;
	private final Function<T,String> stampaBase = x -> x.toString();

	/**
	 * costrttore vuoto di un menu
	 */
	public Menu() {}

	/**
	 * costruttore di un menu
	 * @param titolo : titolo del menu
	 */
	public Menu(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * costruttore di un menu
	 * @param titolo : titolo del menu
	 * @param voci : voci del menu
	 */
	public Menu(String titolo, T[] voci) {
		this.titolo = titolo;
		aggiungiVoci(voci);
	}
	
	/**
	 * costruttore di un menu
	 * @param titolo : titolo del menu
	 * @param voci : voci del menu
	 * @param conclusione : conculsione del menu
	 */
	public Menu(String titolo, T[] voci, String conclusione) {
		this.titolo = titolo;
		this.conclusione = conclusione;
	}
	
	/**
	 * metodo per aggiungere una voce al menu
	 * @param voce : voce da aggiungere
	 */
	public void aggiungiVoce(T voce) {
		this.voci.add(voce);
		nVoci++;
	}

	/**
	 * metodo per aggiungere delle voci al menu
	 * @param voci : voci da aggiungere
	 */
	public void aggiungiVoci(T[] voci) {
		this.voci.addAll(Arrays.asList(voci));
		nVoci += voci.length;
	}

	/**
	 * metodo per aggiungere delle voci al menu
	 * @param voci : voci da aggiungere
	 */
	public void aggiungiVoci(Collection<? extends T> voci) {
		this.voci.addAll(voci);
		nVoci += voci.size();
	}
	
	/**
	 * metodo per rimuovere l'ultima voce del menu
	 * @return voce rimossa
	 */
	public T rimuoviVoce () {
		nVoci--;
		return this.voci.remove();
	}
	
	/**
	 * metodo per rimuovere una voce del menu
	 * @param indice : indice della voce da rimuovere 
	 * @return voce rimossa, null se l'indice non è valido 
	 */
	public T rimuoviVoce (int indice) {
		if (indice <0 || indice>=nVoci)
			return null;
		nVoci--;
		return this.voci.remove(indice);
	}
	
	/**
	 * metodo per rimuovere una voce del menu
	 * @param oggetto : oggetto da rimuovere
	 * @return se la voce da rimuovere esiste
	 */
	public boolean rimuoviVoce (Object oggetto) {
		boolean rimosso = this.voci.remove(oggetto);
		if (rimosso)
			nVoci--;
		return rimosso;
	}
	
	/**
	 * metodo per rimuovere delle voce del menu
	 * @param vociDaRimuovere : vodi da rimuovere
	 * @return true se le voci del menu sono cambiate
	 */
	public boolean rimuoviVoci (Collection<?> vociDaRimuovere) {
		boolean rimosso = this.voci.removeAll(vociDaRimuovere);
		if (rimosso)
			nVoci -= vociDaRimuovere.size();
		return rimosso;
	}
	
	public LinkedList<T> getVoci() {
		return voci;
	}

	public void setVoci(LinkedList<T> voci) {
		this.voci = voci;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getConclusione() {
		return conclusione;
	}

	public void setConclusione(String conclusione) {
		this.conclusione = conclusione;
	}

	public Function<T, String>[] getMetodiStampa() {
		return metodiStampa;
	}

	/**
	 * metodo utilizzao per indicare al menu come stampare le voic, se viene indicato un solo metodo allora questo sarà utilizzato per tutte le voci<br>
	 * se si inserisce null la stampa utilizza il metodo predefinito
	 * @param metodiStampa : metodi da utilizzare per la stampa
	 */
	public void setMetodiStampa(@SuppressWarnings("unchecked") Function<T, String>... metodiStampa) {
		this.metodiStampa = metodiStampa;
	}

	public String getFormatoVoci() {
		return formatoVoci;
	}

	public void setFormatoVoci(String formatoVoci) {
		this.formatoVoci = formatoVoci;
	}

	public String getMessaggioNessunaVoce() {
		return messaggioNessunaVoce;
	}

	public void setMessaggioNessunaVoce(String messaggioNessunaVoce) {
		this.messaggioNessunaVoce = messaggioNessunaVoce;
	}
	
	/**
	 * metodo per stampare a video una scelta
	 * @return voce scelta
	 */
	public int scegli () {
		int numeroScelte = stampa(true,true), ritorno = InputDati.leggiIntero("scelta: ", conclusione == null ? 1 : 0, numeroScelte);
		if (numeroScelte!=nVoci) {
			Iterator<Integer> iterator = saltati.iterator();
			while (iterator.hasNext()){
				if(iterator.next()<ritorno)
					ritorno++;
				else
					break;
			}
		}
		return ritorno;
	}
	
	/**
	 * metodo per stampare a video le voci del menu numerate
	 */
	public void stampaNumerato () {
		stampa(true,false);
	}
	
	/*
	 * metodo per stampare a video le voci del menu
	 */
	public void stampa () {
		stampa(false,false);
	}

	/**
	 * metodo di stampa, stampa le voci del menu secondo i metodi di stampa indicati
	 * @param numera : se le voci devono essere numerate
	 * @param concludi : se stampare la conclusione
	 * @return : voce scelta o 0
	 */
	private int stampa (boolean numera, boolean concludi) {
		boolean controllaMetodoStampa = (metodiStampa==null || metodiStampa.length<2) ? false : true;
		int n=0, i=0;
		saltati.removeAll(saltati);
		if (this.titolo != null)
			System.out.println(this.titolo+'\n');
		Iterator<T> iterator = voci.iterator();
		Function<T,String> funzione = null;
		if(!controllaMetodoStampa)
			funzione = (metodiStampa==null || metodiStampa.length==0) ? stampaBase : metodiStampa[0];
		while (iterator.hasNext()) {
			if(controllaMetodoStampa) {
				if (i<metodiStampa.length && metodiStampa[i] != null)
					funzione = metodiStampa[i];
				else
					funzione = stampaBase;
			}
			String temp = funzione.apply(iterator.next());
			if (temp != null) {
				n++;
				System.out.println(String.format(formatoVoci, numera ? n : " ", temp));
			}
			else
				saltati.add(i);
			i++; 
		}
		if (n==0)
			System.out.println(String.format(formatoVoci, " ", messaggioNessunaVoce));
		if (numera && concludi && conclusione != null)
			System.out.println(String.format(formatoConclusione, 0, this.conclusione));
		System.out.println();
		return n;
	}
}
