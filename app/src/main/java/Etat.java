public class Etat {
    public Etat(String etat) {
        e = etat;
    }

    @Override
    public int hashCode() {

        return e.hashCode();
    }

    public String getE() {
        return e;
    }

    protected final String e ;
}
