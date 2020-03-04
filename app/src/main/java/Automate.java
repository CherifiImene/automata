import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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

    public boolean reconnaitreMot(String mot){
        rendreSimple();
        char[] alphabet = mot.toCharArray();
        Iterator<Instruction> instructionIterator = mInstructions.iterator();
        Instruction instruction;
        int cpt = 0;
        while(instructionIterator.hasNext()){
            instruction = instructionIterator.next();
            if(alphabet[cpt] != instruction.getMotALire().toCharArray()[0]){
                return false;
            }
            cpt++;
        }
        return true;
    }

    private void rendreSimple() {
        Iterator<Instruction> iterator = mInstructions.iterator();
        Instruction instruction;
        while(iterator.hasNext()){
            instruction = iterator.next();
            String motALire = instruction.getMotALire();
            if(motALire.length()>1){
                diviserMot(motALire,instruction);

            }
        }

    }

    private void diviserMot(String motALire,Instruction instruction) {
        char[] alphabets = motALire.toCharArray();
        int cpt = 0 ;
        int poids = instruction.mPoids+1;
        Etat etatActuel = instruction.getEtatActuel();
        Etat etatArrivee;
        while(cpt < motALire.length()){
            etatArrivee = new Etat(instruction.getEtatActuel().getE()+cpt);
            InstructionSimple instructionSimple = new InstructionSimple(alphabets[cpt]);
            instructionSimple.setEtatActuel(etatActuel);
            instructionSimple.setEtatArrivé(etatArrivee);
            instructionSimple.setPoids(poids);
            mInstructions.add(instructionSimple);
            etatActuel = etatArrivee;
            poids++;
            cpt++;
        }

    }

    public void rendreDeterministe(){

        Map<Integer , HashSet<Instruction>> instructionsRegroupees = estDeterministe();
        HashSet<Etat> etats = new HashSet<Etat>();
        HashSet<Instruction> instructionsDeterministe = new HashSet<Instruction>();
        HashSet<Etat> etatsFinalsDeterministe = new HashSet<Etat>();
        boolean estFinal = false;

        if (!deterministe){
            etats.add(mEtatInitial);
            for (Map.Entry<Integer,HashSet<Instruction>> couple : instructionsRegroupees.entrySet()){
                SousEnsembleEtat etat = new SousEnsembleEtat("S"+couple.getKey());
                for (Instruction i:couple.getValue()
                     ) {
                    etat.ajouterEtat(i.getEtatArrivé());
                    if(mEtatFinaux.contains(i.getEtatArrivé()))
                        estFinal = true;
                }
                String mot = couple.getValue().iterator().next().getMotALire();
                Etat actuel = couple.getValue().iterator().next().getEtatActuel();
                etats.add(etat);
                instructionsDeterministe.add(new Instruction(actuel,mot,etat));
                if (estFinal)
                    etatsFinalsDeterministe.add(etat);
            }
            mInstructions = instructionsDeterministe;
            mEtats = etats;
            mEtatFinaux = etatsFinalsDeterministe;
        }

    }

    private boolean estSimple() {
        for (Instruction i:mInstructions
             ) {
            if(i.getMotALire().length()>1)
                return false;

        }
        return true;
    }
    /** Met à jour l'attribut deterministe et regroupe les instructions dans le cas où l'automate n'est pas détérministe**/
    private Map<Integer,HashSet<Instruction>> estDeterministe() {
        int cpt = 0 ;
        int cptInstruction = 0;
        String mot = "";
        Etat etat;
        @SuppressLint("UseSparseArrays") Map<Integer,HashSet<Instruction>> nouvelsEtats = new HashMap<>();
        HashSet<Instruction> instructions = new HashSet<>();
        for (Instruction inst:mInstructions
             ) {
            mot = inst.getMotALire();
            etat = inst.getEtatActuel();
            for (Instruction instruction:mInstructions
                 ) {
                if ((instruction.getEtatActuel().equals(etat))&&(instruction.getMotALire().equals(mot)))
                    cpt++;
                    instructions.add(instruction);

            }
            if (cpt>1) {
                deterministe = false;
                nouvelsEtats.put(cptInstruction,instructions);
                instructions.clear();
            }else{
                deterministe = true;
            }
            cpt = 0;
            cptInstruction++;
        }
        return nouvelsEtats;
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
    private Set<Instruction> mInstructions;     //Utiliser un map<poids,listInstructions>
    private boolean deterministe = false;
    private boolean simple = false;

}
