package de.vanfanel.utils;

import static de.vanfanel.utils.ColorType.HAMA_BASIC;
import static de.vanfanel.utils.ColorType.HAMA_METAL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.Test;

public class BeadsColorUtilsTest {

  private static final Color HAMA_COLOR_BLACK = new HAMAColor("H18", "BLACK", HAMA_BASIC, 0, 0, 0, 255);
  private static final Color HAMA_COLOR_GOLD = new HAMAColor("H61", "GOLD", HAMA_METAL, 170, 135, 75, 255);

  @Test
  public void testGetDistanceBetween2ColorsReturns0IfColorsIdentical() throws Exception {
    Color black = new Color(0,0,0,-123213);
    assertThat(BeadsColorUtils.getDistanceBetween2Colors(black, black), Matchers.equalTo(0d));
  }

  @Test
  public void testGetDistanceBetween2ColorsReturnsRightValueForBlackAndWhiteColor() throws Exception {
    Color black = new Color(0,0,0,-123213);
    Color white = new Color(255,255,255,-123213);
    double distance = 825.8323074329315;
    assertThat(BeadsColorUtils.getDistanceBetween2Colors(black, white), Matchers.equalTo(distance));
  }

  @Test
  public void testGetDistanceBetween2ColorsReturnsRightValueForYellowAndOrangeColor() throws Exception {
    Color black = new Color(255,255,0,-123213);
    Color white = new Color(255,165,0,-123213);
    double distance = 180;
    assertThat(BeadsColorUtils.getDistanceBetween2Colors(black, white), Matchers.equalTo(distance));
  }

  @Test
  public void testGetNearestColorReturnsBlackForBlackColor() throws Exception {
    int black = 587202560;
    HAMAColor blackColor = new HAMAColor("H18","BLACK",0,0,0,255);
    assertThat(BeadsColorUtils.getNearestColor(black, BeadsColorUtils.HAMA_BEADS_COLORS), Matchers.equalTo(blackColor));
  }

  @Test
  public void testGetNearestColorReturnsBlackForBlackColorWithSomeRed() throws Exception {
    int blackWithSomeRed = (1 << 24)  + (16 << 16) + (0 << 8) + 0;
    HAMAColor blackColor = new HAMAColor("H18","BLACK",0,0,0,255);
    assertThat(BeadsColorUtils.getNearestColor(blackWithSomeRed, BeadsColorUtils.HAMA_BEADS_COLORS), Matchers.equalTo(blackColor));
  }

  @Test
  public void testGetNearestColorReturnsRedForRedColor() throws Exception {
    int rgbFullRed = (1 << 24)  + (255 << 16) + (0 << 8) + 0;
    HAMAColor red = new HAMAColor("H05", "RED", 182, 49, 54, 255);
    assertThat(BeadsColorUtils.getNearestColor(rgbFullRed, BeadsColorUtils.HAMA_BEADS_COLORS), Matchers.equalTo(red));
  }

  @Test
  public void testGetNearestColorReturnsYellowForYellowDColor() throws Exception {
    int yellowRGB = (1 << 24)  + (255 << 16) + (255 << 8) + 0;
    HAMAColor hamaYellow = new HAMAColor("H03", "YELLOW", 255, 215, 90, 255);
    assertThat(BeadsColorUtils.getNearestColor(yellowRGB, BeadsColorUtils.HAMA_BEADS_COLORS), Matchers.equalTo(hamaYellow));
  }

  @Test
  public void testColorConvertion() throws Exception {
    Color hamaYellow = new Color(255, 215, 90, 255);
    int yellow = BeadsColorUtils.getIntFromColor(hamaYellow);
    System.out.println(yellow);
    Color colorFromInt = BeadsColorUtils.getColorFromInt(yellow);
    assertThat(hamaYellow, Matchers.equalTo(colorFromInt));
  }

  @Test
  public void testCalculateDimensionToScaleReturnsSameDimensionIfDimensionIsBelow29x29pixels() throws Exception {
    List<Dimension> targetDimensions = BeadsColorUtils.calculateDimensionToScale(new Dimension(29,29));
    assertThat(targetDimensions.size(), Matchers.equalTo(1));
    assertThat(targetDimensions.get(0).getWidth(), Matchers.equalTo(29));
    assertThat(targetDimensions.get(0).getHeight(), Matchers.equalTo(29));
  }

  @Test
  public void testCalculateDimensionToScaleReturnsListWith2DimensionOnDimensionWithMoreThen29x29pixels() throws Exception {
    List<Dimension> targetDimensions = BeadsColorUtils.calculateDimensionToScale(new Dimension(30,30));
    assertThat(targetDimensions.size(), Matchers.equalTo(2));
    assertThat(targetDimensions.get(0).getWidth(), Matchers.equalTo(30));
    assertThat(targetDimensions.get(0).getHeight(), Matchers.equalTo(30));
    assertThat(targetDimensions.get(1).getWidth(), Matchers.equalTo(15));
    assertThat(targetDimensions.get(1).getHeight(), Matchers.equalTo(15));
  }

  @Test
  public void testCalculateDimensionToScaleReturnsListWithMultiDimensionOnDimensionMoreThenMaxSize() throws Exception {
    List<Dimension> targetDimensions = BeadsColorUtils.calculateDimensionToScale(new Dimension(6*29+1,6*29+1)); // 174+1
    assertThat(targetDimensions.size(), Matchers.equalTo(3));
    assertThat(targetDimensions.get(0).getWidth(), Matchers.equalTo(87));
    assertThat(targetDimensions.get(0).getHeight(), Matchers.equalTo(87));
    assertThat(targetDimensions.get(1).getWidth(), Matchers.equalTo(43));
    assertThat(targetDimensions.get(1).getHeight(), Matchers.equalTo(43));
    assertThat(targetDimensions.get(2).getWidth(), Matchers.equalTo(21));
    assertThat(targetDimensions.get(2).getHeight(), Matchers.equalTo(21));
  }

  @Test
  public void testCalculateDimensionToScaleReturnsListWithMultiDimensionOnDimensionWithDifferentHeightAndWidth() throws Exception {
    List<Dimension> targetDimensions = BeadsColorUtils.calculateDimensionToScale(new Dimension(448,502)); // 145+1
    assertThat(targetDimensions.size(), Matchers.equalTo(4));
    assertThat(targetDimensions.get(0).getWidth(), Matchers.equalTo(112));
    assertThat(targetDimensions.get(0).getHeight(), Matchers.equalTo(125));
    assertThat(targetDimensions.get(1).getWidth(), Matchers.equalTo(56));
    assertThat(targetDimensions.get(1).getHeight(), Matchers.equalTo(62));
    assertThat(targetDimensions.get(2).getWidth(), Matchers.equalTo(28));
    assertThat(targetDimensions.get(2).getHeight(), Matchers.equalTo(31));
    assertThat(targetDimensions.get(3).getWidth(), Matchers.equalTo(14));
    assertThat(targetDimensions.get(3).getHeight(), Matchers.equalTo(15));
  }

  @Test
  public void testGetColorsOfColorTypesOnlyReturnsAllowedColorTypes() throws Exception {
    List<ColorType> allowed = new ArrayList<ColorType>(Arrays.asList(ColorType.HAMA_BASIC));
    List<Color> filtered = BeadsColorUtils.getColorsOfColorTypes(allowed);

    assertThat(filtered.contains(HAMA_COLOR_BLACK), Matchers.is(true));
    assertThat(filtered.contains(HAMA_COLOR_GOLD), Matchers.is(false));
  }
}