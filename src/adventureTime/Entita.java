package adventureTime;

public abstract class Entita {
	private String nome;
	private int vita, atk, dif;
	Oggetti oggAttivo = null;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getVita() {
		return vita;
	}
	public void setVita(int vita) {
		this.vita = vita;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDif() {
		return dif;
	}
	public void setDif(int dif) {
		this.dif = dif;
	}
	public Oggetti getOggAttivo() {
		return oggAttivo;
	}
	public void setOggAttivo(Oggetti oggAttivo) {
		this.oggAttivo = oggAttivo;
	}
}
