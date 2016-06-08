package de.vanfanel.utils;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.Test;

public class BeadsColorUtilsTest {

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
    HAMAColor blackColor = new HAMAColor("H18","BLACK",0,0,0,1);
    assertThat(BeadsColorUtils.getNearestColor(black), Matchers.equalTo(blackColor));
  }

  @Test
  public void testGetNearestColorReturnsBlackForBlackColorWithSomeRed() throws Exception {
    int blackWithSomeRed = (1 << 24)  + (16 << 16) + (0 << 8) + 0;
    HAMAColor blackColor = new HAMAColor("H18","BLACK",0,0,0,1);
    assertThat(BeadsColorUtils.getNearestColor(blackWithSomeRed), Matchers.equalTo(blackColor));
  }

  @Test
  public void testGetNearestColorReturnsRedForRedColor() throws Exception {
    int rgbFullRed = (1 << 24)  + (255 << 16) + (0 << 8) + 0;
    HAMAColor red = new HAMAColor("H05", "RED", 182, 49, 54, 1);
    assertThat(BeadsColorUtils.getNearestColor(rgbFullRed), Matchers.equalTo(red));
  }

  @Test
  public void testGetNearestColorReturnsYellowForYellowDColor() throws Exception {
    int yellowRGB = (1 << 24)  + (255 << 16) + (255 << 8) + 0;
    HAMAColor hamaYellow = new HAMAColor("H03", "YELLOW", 255, 215, 90, 1);
    assertThat(BeadsColorUtils.getNearestColor(yellowRGB), Matchers.equalTo(hamaYellow));
  }

  @Test
  public void testColorConvertion() throws Exception {
    Color hamaYellow = new Color(255, 215, 90, 1);
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
    List<Dimension> targetDimensions = BeadsColorUtils.calculateDimensionToScale(new Dimension(5*29+1,5*29+1)); // 145+1
    assertThat(targetDimensions.size(), Matchers.equalTo(3));
    assertThat(targetDimensions.get(0).getWidth(), Matchers.equalTo(73));
    assertThat(targetDimensions.get(0).getHeight(), Matchers.equalTo(73));
    assertThat(targetDimensions.get(1).getWidth(), Matchers.equalTo(36));
    assertThat(targetDimensions.get(1).getHeight(), Matchers.equalTo(36));
    assertThat(targetDimensions.get(2).getWidth(), Matchers.equalTo(18));
    assertThat(targetDimensions.get(2).getHeight(), Matchers.equalTo(18));
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
}