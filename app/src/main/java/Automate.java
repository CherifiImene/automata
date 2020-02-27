import java.util.HashSet;
import java.util.Set;

public class Automate {
    public Automate(){}
    public Automate(Set<Character> alphabet, Set<Etat> etats, Etat etatInitial, Set<Etat> etatFinaux, Set<Instruction> instructions) {
        mAlphabet = alphabet;
        mEtats = etats;

        if(etats.contains(etatInitial)){
            mEtatInitial = etatInitial;
        }

        if(etats.containsAll(etatFinaux)){
            mEtatFinaux = etatFinaux;
        }

        mInstructions = instructions;
    }

    public void reduire(){
        Set<Etat> accessibles = eleminerEtatNonAccessible(this);
        miseAjourEtats(accessibles);
        Set<Etat> coAccessibles = eleminerEtatNonCoAccessible(this);
        miseAjourEtats(coAccessibles);
    }

    private void miseAjourEtats(Set<Etat> accessibles) {
        for (Instruction instruction:this.mInstructions
        ){
            if(!accessibles.contains(instruction.getEtatActuel()))
                this.mInstructions.remove(instruction);
                if(this.mEtatFinaux.contains((instruction.getEtatActuel())))
                    this.mEtatFinaux.remove(instruction.getEtatActuel());
        }
        this.setEtats(accessibles);
    }

    private Set<Etat> eleminerEtatNonCoAccessible(Automate automate) {
        boolean continuer = true;
        Set<Etat> coAccessibles = new HashSet<Etat>(mEtatFinaux);
        while (continuer) {
            continuer = false;
            for (Instruction instruction : automate.mInstructions
            ) {
                if( (coAccessibles.contains(instruction.getEtatArrivé()))&&(!coAccessibles.contains(instruction.getEtatActuel())) ){
                    coAccessibles.add(instruction.getEtatArrivé());
                    continuer = true;
                }


            }
        }
        return coAccessibles;
    }

    private Set<Etat> eleminerEtatNonAccessible(Automate automate) {
        boolean continuer = true;
        Automate a = new Automate();
        Set<Etat> accessibles = new HashSet<Etat>();
        accessibles.add(mEtatInitial);
        while (continuer) {
            continuer = false;
            for (Instruction instruction : automate.mInstructions
            ) {
                if( (accessibles.contains(instruction.getEtatActuel()))&&(!accessibles.contains(instruction.getEtatArrivé())) ){
                    accessibles.add(instruction.getEtatArrivé());
                    continuer = true;
                }


            }
        }
        return accessibles;
    }

    public void setAlphabet(Set<Character> alphabet) {
        mAlphabet = alphabet;
    }

    public void setEtats(Set<Etat> etats) {
        mEtats = etats;
    }

    public void setEtatInitial(Etat etatInitial) {
        mEtatInitial = etatInitial;
    }

    public void setEtatFinaux(Set<Etat> etatFinaux) {
        mEtatFinaux = etatFinaux;
    }

    public void setInstructions(Set<Instruction> instructions) {
        mInstructions = instructions;
    }

    public Set<Character> getAlphabet() {
        return mAlphabet;
    }

    public Set<Etat> getEtats() {
        return mEtats;
    }

    public Etat getEtatInitial() {
        return mEtatInitial;
    }

    public Set<Etat> getEtatFinaux() {
        return mEtatFinaux;
    }

    public Set<Instruction> getInstructions() {
        return mInstructions;
    }

    private Set<Character> mAlphabet;
    private Set<Etat> mEtats;
    private Etat mEtatInitial;
    private Set<Etat> mEtatFinaux;
    private Set<Instruction> mInstructions;

}
