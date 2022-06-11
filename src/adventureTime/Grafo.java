package adventureTime;
import java.util.Collection;
import java.util.Map;

/**
 * classe per rappresentare un grafo
 *
 * @param <N> tipo di nodo
 * @param <A> tipo di peso dell'arco
 */
public interface Grafo<N,A> {
	
	/**
	 * metodo per aggiungere un nodo al grafo
	 * @param nodo : nodo da aggiungere
	 * @return false se il nodo è gia presente
	 */
	public boolean aggiungiNodo (N nodo);
	
	/**
	 * metodo per aggiungere uno o piu nodi al grafo
	 * @param nodo : nodo da aggiungere
	 * @param nodi : nodi da aggiungere
	 * @return numero di nodi gia presenti
	 */
	public default int  aggiungiNodo (N nodo, @SuppressWarnings("unchecked") N... nodi)  {
		int insuccessi = 0;
		if(!aggiungiNodo(nodo))
			insuccessi++;
		for (N _nodo : nodi)
				if(!aggiungiNodo(_nodo))
				insuccessi++;
		return insuccessi;
	}
	
	/**
	 * metodo per rimuovere un nodo da un grafo
	 * @param nodo : nodo da rimuovere
	 */
	public void rimuoviNodo (N nodo);
	
	/**
	 * metodo per impostare direzione e peso di un arco
	 * @param nodoPartenza : nodo di partenza dell'arco
	 * @param nodoArrivo : nodo di arrivo dell'arco
	 * @param valore : peso dell'erco
	 */
	public void impostaArco (N nodoPartenza, N nodoArrivo, A valore);
	
	/**
	 * metodo per impostare direzione e peso di uno o piu archi
	 * @param nodoPartenza : nodo di partenza dell'arco
	 * @param nodoArrivo : nodo di arrivo dell'arco
	 * @param valore : peso dell'erco
	 * @param PartenzaArrivoValore : successive triplette di: nodo di parteza, nodo di arrivo, valore
	 */
	@SuppressWarnings("unchecked")
	public default void impostaArco (N nodoPartenza, N nodoArrivo, A valore, Object... PartenzaArrivoValore) {
		impostaArco(nodoPartenza, nodoArrivo, valore);
		for (int i=0; i+2<PartenzaArrivoValore.length; i++) {
			try {
			impostaArco((N)PartenzaArrivoValore[i++],(N)PartenzaArrivoValore[i++],(A)PartenzaArrivoValore[i]);
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	/**
	 * metodo per rimuovere un'arco da un grafo
	 * @param nodoPartenza : nodo di partenza dell'arco
	 * @param nodoArrivo : nodo di arrivo dell'arco
	 */
	public void rimuoviArco (N nodoPartenza, N nodoArrivo);
	
	/**
	 * metodo per ottenere il peso di un arco da un grafo
	 * @param nodoPartenza : nodo di partenza dell'arco
	 * @param nodoArrivo : nodo di arrivo dell'arco
	 * @return peso dell'arco congiungete il nodo di partenza e il nodo di arrivo (direzionalmente)
	 */
	public A ottieniArco (N nodoPartenza, N nodoArrivo);
	
	/**
	 * metodo per settare i nodi di un grafo
	 * @param nodi : nodi del grafo
	 */
	public void impostaNodi (Collection<? extends N> nodi);
	
	/**
	 * metodo per ottenere i nodi del grafo
	 * @return
	 */
	public Collection<N> ottieniNodi ();
	
	/**
	 * metodo per settare gli archi di un grafo
	 * @param archi
	 */
	public void impostaArchi ( Map<? extends N, ? extends Map<? extends N, ? extends A>> archi);
	
	/**
	 * metodo per ottenere gli archi di un grafo
	 * @return una mappa rappresentate i collegamenti del grafo
	 */
	public  Map<N, Map<N, A>> ottieniArchi ();
	
	/**
	 * metodo per ottenre gli archi uscenti da un nodo
	 * @param nodo : nodo di cui ottenre gli archi
	 * @return una mappa rappresentante i collegamenti dal nodo agli altri
	 */
	public Map<N, A> archiDiNodo (N nodo);
}
