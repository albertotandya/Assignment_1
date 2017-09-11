public class TrumpCards extends Cards {
    public TrumpCards(String name) {
        super((name));
    }       //creating the constructor

    public String cardEffect() {        //to apply the effect of the supertrump card to the game
        String effect = "";
        String a = getCardName();
        if(a.equals("The Mineralogist")) {
            effect = "C";
        }
        else if(a.equals("The Geologist")) {
            effect = "ANY";
        }
        else if(a.equals("The Geophysicist")) {
            effect = "SG";
        }
        else if(a.equals("The Petrologist")) {
            effect = "CA";
        }
        else if(a.equals("The Miner")) {
            effect = "EV";
        }
        else if(a.equals("The Gemmologist")) {
            effect = "H";
        }
        return effect;
    }

    public String cardEffectDesc() {        //to give the effect description of the supertrump card
        String desc = "";
        String a = getCardName();
        if(a.equals("The Mineralogist")) {
            desc = "Category change to cleavage";
        }
        else if(a.equals("The Geologist")) {
            desc = "Category change to player's choice";
        }
        else if(a.equals("The Geophysicist")) {
            desc = "Category change to specific gravity or throw magnetite";
        }
        else if(a.equals("The Petrologist")) {
            desc = "Category change to crustal abundance";
        }
        else if(a.equals("The Miner")) {
            desc = "Category change to economic value";
        }
        else if(a.equals("The Gemmologist")) {
            desc = "Category change to hardness";
        }
        return desc;
    }
}
