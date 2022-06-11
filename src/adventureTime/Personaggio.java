package adventureTime;

import java.util.HashMap;
import java.util.Map;

public class Personaggio extends Entita {
	
	private Map<Oggetti,Integer> inventario = new HashMap<Oggetti,Integer> ();
	
	Personaggio () {
		super();
		this.setVita(20);
		this.setAtk(5);
		this.setDif(5);
	}
	
	public String aggiungiOgg (Oggetti oggDaAggiungere) {
		if(inventario.size() == 6 && oggettoPresente(oggDaAggiungere) == null) {
			return "Inventario pieno";
		}
		if(oggDaAggiungere.getTipoOgg()==Oggetti.Tipo.Arma || oggDaAggiungere.getTipoOgg()==Oggetti.Tipo.Scudo) {
			Oggetti temp;
			if ((temp = oggettoPresente(oggDaAggiungere)) != null) {
				if (InputDati.yesOrNo(String.format("Sostituire %s con %s?", temp.getNomeOgg(), oggDaAggiungere.getNomeOgg()))) {
					inventario.remove(temp);
					inventario.put(oggDaAggiungere, 1);
					return String.format("%s sostituito a %s", oggDaAggiungere.getNomeOgg(), temp.getNomeOgg());
				}
				else
					return String.format("%s scartato", oggDaAggiungere.getNomeOgg());
			}
			else {
				inventario.put(oggDaAggiungere, 1);
				return String.format("Aggiunto %s", oggDaAggiungere.getNomeOgg());
			}
		}
		else {
			Oggetti temp;
			if ((temp = oggettoPresente(oggDaAggiungere)) != null)
				inventario.put(temp, inventario.get(temp) + 1);
			else {
				temp = oggDaAggiungere;
				inventario.put(oggDaAggiungere, 1);
			}
			return String.format("Aggiunto %s(%d)", temp.getNomeOgg(), inventario.get(temp));
		}
	}
	
	public String rimuoviOgg (Oggetti oggDaRimuovere) {
		oggDaRimuovere = oggettoPresente(oggDaRimuovere);
		inventario.put(oggDaRimuovere, inventario.get(oggDaRimuovere) - 1);
		if (inventario.get(oggDaRimuovere) == 0) { 
			inventario.remove(oggDaRimuovere);
			if (oggDaRimuovere.getTipoOgg() == Oggetti.Tipo.Consumabile)
				return String.format("%s esaurito", oggDaRimuovere.getNomeOgg());
			else
				return String.format("Hai lasciato %s", oggDaRimuovere.getNomeOgg());
		}
		return String.format("consumato %s (%d)", oggDaRimuovere.getNomeOgg(), inventario.get(oggDaRimuovere));
	}

	private Oggetti oggettoPresente(Oggetti oggetoDaControllare) {
		for (Map.Entry<Oggetti,Integer> confronto : inventario.entrySet())
			if(confronto.getKey().getNomeOgg().equals(oggetoDaControllare.getNomeOgg()) || // controllo per nome identificativo oggetto
					((oggetoDaControllare.getTipoOgg() == Oggetti.Tipo.Arma || oggetoDaControllare.getTipoOgg() == Oggetti.Tipo.Scudo) &&
							confronto.getKey().getTipoOgg() == oggetoDaControllare.getTipoOgg())) // controllo per tipo
				return confronto.getKey();
		return null;
	}
	
	public Map<Oggetti,Integer> getInventario () {
		return inventario;
	}
}
