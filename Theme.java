import java.awt.*;
import java.util.Random;
/**
 * Write a description of class Theme here.
 *
 * @author Spencer Moon and Micheal Plekan
 * @version Spring 2021
 */
public class Theme
{
    public static final Color[] FIRST_COLORS = {new Color(255, 228, 179), Color.WHITE, new Color(255, 249, 209), new Color(255, 249, 209), new Color(247, 204, 255), Color.WHITE};
    public static final Color[] SECOND_COLORS = {new Color(158, 107, 71), new Color(100, 100, 100), new Color(255, 142, 51), new Color(52, 173, 82), new Color(141, 56, 201), new Color(255, 184, 230)};
    public Color validMove1;
    public Color validMove2;
    private static Random rand = new Random();

    public static Color getRandomMono()
    {

        int red = rand.nextInt(176) + 80;
        int green = rand.nextInt(176) + 80;
        int blue = rand.nextInt(176) + 80;
        while (red + green + blue < 410 || (red < 235 && green < 235 && blue < 235))
        {
            red = rand.nextInt(176) + 80;
            green = rand.nextInt(176) + 80;
            blue = rand.nextInt(176) + 80;
        }

        return new Color(red, green, blue);
    }

    public static Color[] getRandomBi()
    {

        int red = rand.nextInt(176) + 80;
        int green = rand.nextInt(176) + 80;
        int blue = rand.nextInt(176) + 80;
        while (red + green + blue < 410 || (red < 235 && green < 235 && blue < 235))
        {
            red = rand.nextInt(176) + 80;
            green = rand.nextInt(176) + 80;
            blue = rand.nextInt(176) + 80;
        }
        int red2 = rand.nextInt(176) + 80;
        int green2 = rand.nextInt(176) + 80;
        int blue2 = rand.nextInt(176) + 80;
        while (red2 + green2 + blue2 < 410 || (red2 < 235 && green2 < 235 && blue2 < 235) || (Math.abs(red - red2) < 40 && Math.abs(green - green2) < 40 && Math.abs(blue - blue2) < 40))
        {
            red2 = rand.nextInt(176) + 80;
            green2 = rand.nextInt(176) + 80;
            blue2 = rand.nextInt(176) + 80;
        }

        Color[] colors = {new Color(red, green, blue), new Color(red2, green2, blue2)};
        return colors;

    }

}
