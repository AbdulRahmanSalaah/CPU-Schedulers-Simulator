package scheduler;
public class ColorUtils {

    private static final String[] COLORS = {
        "#3357FF", // Blue
        "#FF33A8", // Pink
        "#A833FF", // Purple
        "#33FFF6", // Cyan
        "#FF8F33", // Orange
        "#8FFF33", // Lime
        "#FF3333", // Red
        "#33FF8F"  // Mint
    };

    private static int colorIndex = 0;

    public static String getRandomColor() {
        String color = COLORS[colorIndex % COLORS.length];
        colorIndex++;
        return color;
    }
}
