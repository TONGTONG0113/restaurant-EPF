package fr.epf.restaurant.exception;

public class StatutInvalideException extends RuntimeException {

    private final String statutActuel;
    private final String statutAttendu;

    public StatutInvalideException(String message) {
        super(message);
        this.statutActuel = null;
        this.statutAttendu = null;
    }

    public StatutInvalideException(String statutActuel, String statutAttendu, String contexte) {
        super("Transition invalide pour " + contexte
                + " : actuel='" + statutActuel + "', attendu='" + statutAttendu + "'");
        this.statutActuel = statutActuel;
        this.statutAttendu = statutAttendu;
    }

    public String getStatutActuel() {
        return statutActuel;
    }

    public String getStatutAttendu() {
        return statutAttendu;
    }
}
