package de.vanfanel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeadsColorUtils {

  public static final List<HAMAColor> HAMA_BEADS_COLORS = new ArrayList<>();
  public static final HAMAColor HAMA_TRANSPARENT = new HAMAColor("T","TRANSPARENT",0,0,0,0);

  public static final Dimension MAX_SIZE = new Dimension(5*29,5*29);
  public static final Dimension MIN_RESIZE_VALUE = new Dimension(29,29);

  static{

    HAMA_BEADS_COLORS.add(new HAMAColor("H01", "WHITE", 255, 255, 255, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H02", "CREAM", 250, 240, 195, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H03", "YELLOW", 255, 215, 90, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H04", "ORANGE", 240, 105, 95, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H05", "RED", 182, 49, 54, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H06", "PINK", 245, 155, 175, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H07", "PURPLE", 120, 90, 145, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H08", "BLUE", 35, 85, 160, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H09", "LIGHT BLUE", 25, 105, 180, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H10", "GREEN", 35, 125, 95, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H11", "LIGHT GREEN", 70, 195, 165, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H12", "BROWN", 100, 75, 80, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H17", "GREY", 145, 150, 155, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H18", "BLACK", 0, 0, 0, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H20", "BROWN", 170, 85, 80, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H21", "LIGHT BROWN", 190, 130, 100, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H22", "DARK RED", 175, 75, 85, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H26", "FLESH", 240, 170, 165, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H27", "BEIGE", 225, 185, 150, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H28", "DARK GREEN", 70, 85, 90, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H29", "CLARET", 195, 80, 115, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H30", "BURGUNDY", 115, 75, 85, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H31", "TURQUOISE", 105, 160, 175, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H32", "FUCHSIA", 255, 95, 200, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H43", "PASTEL YELLOW", 245, 240, 125, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H44", "PASTEL RED", 255, 120, 140, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H45", "PASTEL PURPLE", 165, 140, 205, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H46", "PASTEL BLUE", 80, 170, 225, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H47", "PASTEL GREEN", 150, 230, 160, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H48", "PASTEL PINK", 230, 135, 200, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H49", "AZURE", 73, 152, 188, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H60", "TEDDY BEAR", 240, 175, 95, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H61", "GOLD", 170, 135, 75, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H62", "SILVER", 175, 180, 190, 255));
    HAMA_BEADS_COLORS.add(new HAMAColor("H63", "BRONZE", 170, 160, 105, 255));
  }

  public static int getIntFromColor(Color color)
  {
    return getIntFromColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  public static int getIntFromColor(int red, int green, int blue, int alpha)
  {
    return (alpha << 24)
        | (red << 16)
        | (green << 8)
        | (blue);
  }

  public static Color getColorFromInt(int color)
  {
    int alpha = (color >> 24) & 0xFF;
    int red = (color >> 16) & 0xFF;
    int green = (color >> 8) & 0xFF;
    int blue = color & 0xff;
    return new Color(red,green, blue, alpha);
  }

  /**
   * Calculate the difference between 2 colors with a fast algorithmus.
   * It's function is explained on http://www.compuphase.com/cmetric.htm
   * The Distance is not the standard distance in the RGB Color palette, its
   * also based on the humans ability to see colors.
   * The distance is not just an Euclidean distance. Its an weighted Euclidean distance.
   *
   *
   * @param a the base color
   * @param b the color to compare with
   * @return the distance between the colors a and b
   */
  public static double getDistanceBetween2Colors(Color a, Color b)
  {
    long redColorFactor = ((long) a.getRed() + (long) b.getRed() ) / 2L;
    long deltaRed   = a.getRed() - b.getRed();
    long deltaGreen = a.getGreen() - b.getGreen();
    long deltaBlue  = a.getBlue() - b.getBlue();

    return Math.sqrt(
        (((512 + redColorFactor) *deltaRed*deltaRed ) >>8)
            + 4*deltaGreen*deltaGreen
            + (((767 - deltaRed) * deltaBlue * deltaBlue) >> 8)
    );

  }

  public static HAMAColor getNearestColor(int rgbaColor)
  {
    Color toCheck = getColorFromInt(rgbaColor);

    if(toCheck.getAlpha() == 0){
      return HAMA_TRANSPARENT;
    }

    Map<HAMAColor, Double> distances = HAMA_BEADS_COLORS.parallelStream().collect(Collectors.toMap(
      HAMAColor::getThisObj , c ->  getDistanceBetween2Colors(c, toCheck)
    ));

    return distances.entrySet().parallelStream()
        .min((c1, c2) -> Double.compare(c1.getValue(), c2.getValue())).get().getKey();

  }

  public static List<Dimension> calculateDimensionToScale(Dimension start) {
    List<Dimension> results = new ArrayList<>();
    Dimension currentDimension = start;

    if(currentDimension.getWidth() < MAX_SIZE.getWidth() && currentDimension.getHeight() < MAX_SIZE.getHeight()){
      results.add(currentDimension.clone());
    }

    while( currentDimension.getWidth() > MIN_RESIZE_VALUE.getWidth() ||
        currentDimension.getHeight() > MIN_RESIZE_VALUE.getHeight() )
    {
      currentDimension = new Dimension(currentDimension.getWidth() / 2, currentDimension.getHeight() / 2);

      if(currentDimension.getWidth() < MAX_SIZE.getWidth() && currentDimension.getHeight() < MAX_SIZE.getHeight()){
        results.add(currentDimension.clone());
      }
    }

    return results;
  }
}
