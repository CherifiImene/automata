import androidx.annotation.Nullable;

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
    public void setPoids(int poids) {
        mPoids = poids;
    }

    public Etat getEtatActuel() {
        return mEtatActuel;
    }

    public String getMotALire() {
        return mMotALire;
    }

    @Override
    public int hashCode() {
        return mEtatActuel.hashCode()+mMotALire.hashCode()+mEtatArrivé.hashCode();
    }

    public Etat getEtatArrivé() {
        return mEtatArrivé;
    }
    public int getPoids() {
        return mPoids;
    }

    public Instruction(Etat etatActuel, String motALire, Etat etatArrivé) {
        mEtatActuel = etatActuel;
        mMotALire = motALire;
        mEtatArrivé = etatArrivé;
    }
    public Instruction(){

    }

    public boolean equals( Instruction obj) {
        return (mPoids == obj.getPoids());
    }

    protected Etat mEtatActuel;
    private String mMotALire;
    protected Etat mEtatArrivé;
    protected int mPoids = 0;
}
