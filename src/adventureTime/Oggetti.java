package adventureTime;


/**
 * classe per rappresentare gli oggetti
 * @author apost
 *
 */
public class Oggetti {

	private String nomeOgg;
	private Tipo tipoOgg;
	private int attributo;

	Oggetti() { }
	
	Oggetti(String nomeOgg, Tipo tipoOgg) {
		this.nomeOgg = nomeOgg;
		this.tipoOgg = tipoOgg;
	}

	Oggetti(String nomeOgg, Tipo tipoOgg, int attributo) {
		this.nomeOgg = nomeOgg;
		this.tipoOgg = tipoOgg;
		this.attributo = attributo;
	}

	public String getNomeOgg() {
		return nomeOgg;
	}


	public void setNomeOgg(String nomeOgg) {
		this.nomeOgg = nomeOgg;
	}


	public Tipo getTipoOgg() {
		return tipoOgg;
	}


	public void setTipoOgg(Tipo tipoOgg) {
		this.tipoOgg = tipoOgg;
	}

	public int getAttributo() {
		return attributo;
	}

	public void setAttributo(int attributo) {
		this.attributo = attributo;
	}

	public enum Tipo {
		Consumabile,
		Arma,
		Scudo;
	}
}
