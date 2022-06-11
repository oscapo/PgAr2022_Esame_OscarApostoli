package adventureTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * classe per rappresentare un grafo, utilizzante una mappa per tracciare le relazinio fra nodi
 *
 * @param <N> tipo di nodo
 * @param <A> tipo di peso dell'arco
 */
public class GrafoMappato<N, A> implements Grafo<N, A> {
	
	/**
	 * mappa degli archi
	 */
	Map<N, Map<N, A>> archi = new HashMap<N, Map<N, A>>();
	
	/**
	 * costruttore vuoto di GrafoMappato
	 */
	public GrafoMappato() { }
	
	/**
	 * costruttore di GrafoMappato, istanzainte un grafo equivalente a quello in ingresso
	 * @param grafo : grafo di cui creare un'istanza equivalente
	 */
	public GrafoMappato(Grafo<? extends N, ? extends A> grafo) {
		this.impostaNodi(grafo.ottieniNodi());
		this.impostaArchi(grafo.ottieniArchi());
	}
	
	@Override
	public boolean aggiungiNodo(N nodo) {
		archi.put(nodo, new HashMap<N,A>());
		return false;
	}

	@Override
	public void rimuoviNodo(N nodo) {
		archi.remove(nodo);
	}

	@Override
	public void impostaArco(N nodoPartenza, N nodoArrivo, A valore) {
		archi.get(nodoPartenza).put(nodoArrivo, valore);
	}

	@Override
	public void rimuoviArco(N nodoPartenza, N nodoArrivo) {
		archi.get(nodoPartenza).remove(nodoArrivo);
	}

	@Override
	public A ottieniArco(N nodoPartenza, N nodoArrivo) {
		return archi.get(nodoPartenza).get(nodoArrivo);
	}

	@Override
	public void impostaNodi(Collection<? extends N> nodi) {
		archi.clear();
		for (N nodo : nodi)
			this.aggiungiNodo(nodo);
	}

	@Override
	public Collection<N> ottieniNodi() {
		return archi.keySet();
	}

	@Override
	public void impostaArchi(Map<? extends N, ? extends Map<? extends N, ? extends A>> archi) {
		for(Map.Entry<? extends N, ? extends Map<? extends N, ? extends A>> valoriNodo : archi.entrySet())
			for(Map.Entry<? extends N, ? extends A> valore : valoriNodo.getValue().entrySet())
				this.archi.get(valoriNodo.getKey()).put(valore.getKey(), valore.getValue());
	}

	@Override
	public Map<N, Map<N, A>> ottieniArchi() {
		return archi;
	}

	@Override
	public Map<N, A> archiDiNodo(N nodo) {
		return archi.get(nodo);
	}

}
