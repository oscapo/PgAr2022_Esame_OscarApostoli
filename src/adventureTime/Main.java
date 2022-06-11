package adventureTime;

public class Main {

	private static final String GIOCA = "Nuova partita";
	private static final String ESCI = "Esci dal gioco";
	private static final String[] VOCI_MENU_PRINCIPALE = {GIOCA};
	private static Menu<String> MENU_PRINCIPALE = new Menu<String> ("Menu principale", VOCI_MENU_PRINCIPALE);
	public static void main (String[] Args) {
		MENU_PRINCIPALE.setConclusione(ESCI);
		for(;;) {
			int scelta = MENU_PRINCIPALE.scegli() -1;
			if (scelta ==-1)
				return;
			Partita partita = new Partita();
			partita.gioca();
		}
	}
}
