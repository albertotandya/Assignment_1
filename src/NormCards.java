public class NormCards extends Cards {
    private float hardness;
    private float specGravity;
    private String cleavage;
    private int cleavageVal;
    private String crustalAbun;
    private int crustalAbunVal;
    private String ecoValue;
    private int ecoValueValue;      //defining the variables

    public NormCards (String name, float hard, float specGrav,String cle, String crustAbun, String ecoVal) {
        super(name);
        hardness = hard;
        specGravity = specGrav;
        cleavage = cle;
        crustalAbun = crustAbun;
        ecoValue = ecoVal;
        cleavageVal = cleavageValueConverter();
        crustalAbunVal = abundaceValueConverter();
        ecoValueValue = ecoValueConverter();
    }       //creating the constructor

    public float getHardness() {
        return hardness;
    }

    public float getSpecGravity() {
        return specGravity;
    }

    public String getCleavage() {
        return cleavage;
    }

    public int getCleavageValue() {
        return cleavageVal;
    }

    public String getCrustalAbun() {
        return crustalAbun;
    }

    public int getCrustalAbunVal() {
        return crustalAbunVal;
    }

    public String getEcoValue() {
        return ecoValue;
    }

    public int getEcoValueValue() {
        return ecoValueValue;
    }       //getter for all of the variables

    private int cleavageValueConverter() {      //to convert the cleavage into integer value, so it can be compared
        int val = 0;
        String a = getCleavage();
        if (a.equals("none")) {
            val = 1;
        } else if (a.equals("poor/none")) {
            val = 2;
        } else if (a.equals("1 poor")) {
            val = 3;
        } else if (a.equals("2 poor")) {
            val = 4;
        } else if (a.equals("1 good")) {
            val = 5;
        } else if (a.equals("1 good/1 poor")) {
            val = 6;
        } else if (a.equals("2 good")) {
            val = 7;
        } else if (a.equals("3 good")) {
            val = 8;
        } else if (a.equals("1 perfect")) {
            val = 9;
        } else if (a.equals("1 perfect/1 good")) {
            val = 10;
        } else if (a.equals("1 perfect/2 good")) {
            val = 11;
        } else if (a.equals("2 perfect/1 good")) {
            val = 12;
        } else if (a.equals("3 perfect")) {
            val = 13;
        } else if (a.equals("4 perfect")) {
            val = 14;
        } else if (a.equals("6 perfect")) {
            val = 15;
        }
        return val;
    }

    private int abundaceValueConverter() {      //to convert the crustal abundance into integer value, so it can be compared
        int val = 0;
        String a = getCrustalAbun();
        if(a.equals("ultratrace")) {
            val=1;
        }
        else if(a.equals("trace")) {
            val=2;
        }
        else if(a.equals("low")) {
            val=3;
        }
        else if(a.equals("moderate")) {
            val=4;
        }
        else if(a.equals("high")) {
            val=5;
        }
        else if(a.equals("very high")) {
            val=6;
        }
        return val;
    }

    private int ecoValueConverter() {       //to convert the economic value into integer value, so it can be compared
        int val = 0;
        String a = getEcoValue();
        if(a.equals("trivial")) {
            val=1;
        }
        else if(a.equals("low")) {
            val=2;
        }
        else if(a.equals("moderate")) {
            val=3;
        }
        else if(a.equals("high")) {
            val=4;
        }
        else if(a.equals("very high")) {
            val=5;
        }
        else if(a.equals("I'm rich!")) {
            val=6;
        }
        return val;
    }
}
