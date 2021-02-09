import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static String[] onePlus = {"H1+", "Li1+", "Na1+", "K1+", "Rb1+", "Cs1+", "Fr1+", "Ag1+", "Cu1+", "Hg1+"};
    static String[] twoPlus = {"Mg2+", "Ca2+", "Sr2+", "Ba2+", "Zn2+", "Cd2+", "Cr2+", "Co2+", "Cu2+", "Fe2+", "Pb2+", "Mn2+", "Hg2+", "Sn2+"};
    static String[] threePlus = {"Al3+, Bi3+", "Cr3+", "Co3+", "Fe3+", "Mn3+"};
    static String[] fourPlus = {"Pb4+", "Mn4+", "Sn4+"};
    static String[] oneMinus = {"H1-", "F1-", "Cl1-", "Br1-", "I1-"};
    static String[] twoMinus = {"O2-", "S2-"};
    static String[] threeMinus = {"N3-", "P3-"};
    static String[] fourMinus = {"C4-"};
    static String[][] anions = {oneMinus, twoMinus, threeMinus, fourMinus};
    static String[][] cations = {onePlus, twoPlus, threePlus, fourPlus};
    static String[][][] ions = {anions, cations};
    public static void main(String[] args) {
        Main run = new Main();
        p(run.combine("O2-"));
    }

    /**
     * @param ion1 such as N3-, O2-, Mg2+, Cr3+, Li1+, Al3+, etc.
     * @return some physically possible polyatomic neutral molecules containing ion1.
     * For example, combine(Al3+) returns
     * AlH3,
     * AlF3,
     * AlCl3,
     * AlBr3,
     * AlI3,
     * Al2O3,
     * Al2S3,
     * AlN,
     * AlP,
     * Al4C3
     *
     * Note that it will list the parameter ion first in the molecule. Therefore,
     * Fe2O3 will be represented as O3Fe2, etc.
     * It covers these elements:
     * H Li Na K Rb Cs Fr Ag Cu Hg Mg Ca Sr Ba Zn Cd Cr Co Cu Fe Mn Sn Al Bi Pb F Cl Br I O S N P C
     */
    private ArrayList<String> combine(String ion1) {
        ArrayList<String> combinations = new ArrayList<>();
        String elementalSymbol = reformat(ion1);
        p(elementalSymbol);
        int[] specs = getSpecs(ion1);
        p(Arrays.toString(specs));
        int relCharge = specs[0];
        int opposite = Integer.MIN_VALUE;
        if (relCharge == 0){opposite = 1;}
        if (relCharge == 1){opposite = 0;}
        String[][] oppositeOfIon = ions[opposite];
        String possibleCharges = "1234";
        int absCharge = specs[1];
        for (String[] ionizationGroup: oppositeOfIon) {
            p(Arrays.toString(ionizationGroup));
        }
        p(Arrays.deepToString(oppositeOfIon));
        for (String[] oneAbsCharges : oppositeOfIon) {
            for (String absCharge1 : oneAbsCharges) {
                int[] ionSpecs = getSpecs(absCharge1);
                //p("ionSpecs: " + Arrays.toString(ionSpecs));
                int lcm = lcm(specs[1], ionSpecs[1]);
                int suffix1 = lcm / specs[1];
                int suffix2 = lcm / ionSpecs[1];
                String combinable = reformat(absCharge1);
                StringBuilder combinedYAY = new StringBuilder();
                // StringBuilder combinedYAY2 = new StringBuilder();
                String RomanNumeral = RomanNumeralOf(specs[1]);
                combinedYAY.append(elementalSymbol).append(suffix1).append(combinable).append(suffix2);
                String YAYYYYYY = combinedYAY.toString().replace("1", "");
                //combinedYAY2.append(elementalSymbol).append("(").append(RomanNumeral).append(")").append(combinable).append(specs[1]);
                combinations.add("\n" + YAYYYYYY);
            }
        }
        return combinations;
    }

    /**
     * @param int1
     * @param int2
     * @return least common multiple of int1 and int2
     */
    private int lcm(int int1, int int2){
        int LCM = 0;
        for (int i = 1; i < 100; i++) {
            if ((i % int1 == 0) && (i % int2 == 0)){
                LCM = i;
                break;
            }
        }
        return LCM;
    }

    /**
     * Removes [0-9], "+", and "-".
     * @param string to be reformatted
     * @return string without numbers, + or -.
     */
    private String reformat(String string){
        return string.replaceAll("[0-9]", "").replace("-", "").replace("+", "");
    }
    private String RomanNumeralOf(int ArabicNumeral){
        String[] romanNumerals = {"I", "II", "III", "IV"};
        return romanNumerals[ArabicNumeral-1];
    }

    /**
     * @param ion e.g. H1+ is the helium monatomic 1+ cation
     * @return negativity-positivity in form of 0 or 1, and absolute charge in form of 1,2,3, or 4
     * Therefore ion H1+ would return [1,1] for isCationic and 1 absolute charge.
     */
        private int[] getSpecs (String ion){
                int isCationic = Integer.MIN_VALUE; //isCationic 0 if ion is anionic, 1 if ion is cationic.
                int charge = Integer.MIN_VALUE; //charge the absolute value
            /*
            is Integer.MIN_VALUE for the heck of it...but also isCationic can't be zero,
            because that is the boolean for anionic.
            Why not use booleans...
            */
                if (ion.contains("+")) {
                    isCationic = 1;
                }
                if (ion.contains("-")) {
                    isCationic = 0;
                }
                String[][] typIonic = ions[isCationic];
                for (int i = 1; i < 5; i++) {
                    if (ion.contains(String.valueOf(i))) {
                        charge = i;
                    }
                }
            return new int[]{isCationic, charge};
            }
            private static void p(Object object_to_print){
                System.out.println(object_to_print);
            }
    }

