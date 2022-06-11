package adventureTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mostro extends Entita {
	
	public static Mostro generaMostro() {
		Random rand = new Random();
		Mostro ritorno = new Mostro();
		String nome="dijkstra";
		List<Character> nomeList = new ArrayList<Character>();
		for(char a : nome.toCharArray())
			nomeList.add(a);
		Collections.shuffle(nomeList);
		nomeList.set(0, Character.toUpperCase(nomeList.get(0)));
		nome = "";
		for (char a: nomeList)
			nome += a;
		ritorno.setNome(nome);
		ritorno.setVita(rand.nextInt(15,26));
		ritorno.setAtk(5);
		ritorno.setDif(5);
		Oggetti arma = new Oggetti("Arma",Oggetti.Tipo.Arma);
		arma.setAttributo(rand.nextInt(35,56));
		ritorno.setOggAttivo(arma);
		return ritorno;
	}
}
