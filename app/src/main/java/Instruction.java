import java.util.Set;

public class Instruction {
    public void setEtatActuel(Etat etatActuel) {
        mEtatActuel = etatActuel;
    }

    public void setMotALire(String motALire) {
        mMotALire = motALire;
    }

    public void setEtatArrivé(Etat etatArrivé) {
        mEtatArrivé = etatArrivé;
    }

    public Etat getEtatActuel() {
        return mEtatActuel;
    }

    public String getMotALire() {
        return mMotALire;
    }

    public Etat getEtatArrivé() {
        return mEtatArrivé;
    }

    private Etat mEtatActuel;
    private String mMotALire;
    private Etat mEtatArrivé;
}
