package drawing;

public final class Utils {

    private static final int THREE = 3;
    private static final int FOUR = 4;

    private Utils() {
    }
    // functie de procesare a inputului de culoare ce returneaza un vector
    public static int[] rgb(final String str) {
        int r = Integer.parseInt("" + str.charAt(1) + str.charAt(2), FOUR * FOUR);
        int g = Integer.parseInt("" + str.charAt(THREE) + str.charAt(FOUR), FOUR * FOUR);
        int b = Integer.parseInt("" + str.charAt(FOUR + 1)
            + str.charAt(THREE * 2), FOUR * FOUR);

        return new int[] {r, g, b};
    }

}
