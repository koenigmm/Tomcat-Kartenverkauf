package kartenverkauf;

public class Sitzplatz {
	private Zustand zustand;
	private int sitznummer;
	private String reservierungsname;
	private Boolean verkaufbar;

	public Sitzplatz(Zustand _zustand, int _sitznummer, boolean _verkaufbar) {
		zustand = _zustand;
		sitznummer = _sitznummer;
		verkaufbar = _verkaufbar;
	}
	
	public synchronized Zustand getZustand() {
		return zustand;
	}
	
	public synchronized int getSitznummer() {
		return sitznummer;
	}
	
	public synchronized String getReservierungsname() {
		return reservierungsname;
	}
	
	public synchronized boolean getVerkaufbar() {
		return verkaufbar;
	}
	
	public synchronized void setZustand(Zustand zustandToSet) {
		switch (zustandToSet) {
		case FREI:
			zustand = Zustand.FREI;
			break;
		case RESERVIERT:
			zustand = Zustand.RESERVIERT;
			break;
		case VERKAUFT:
			zustand = Zustand.VERKAUFT;
			break;
		default:
			System.out.println("Bitte gültigen Zustand angeben");
			break;
		}
	}
	
	public synchronized void setVerkaufbar(boolean verkaufbarValue) {
		verkaufbar = verkaufbarValue;
	}
	
	public synchronized void setReservierungsName(String name) {
		reservierungsname = name;
	}
	
	public synchronized void clearReservierungsName() {
		reservierungsname = null;
	}
}
