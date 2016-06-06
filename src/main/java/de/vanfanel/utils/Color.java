package de.vanfanel.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Color {

  private int red;

  private int green;

  private int blue;

  private int alpha;

  public Color() {
  }

  public Color(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public int getRed() {
    return red;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public int getGreen() {
    return green;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public int getBlue() {
    return blue;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  public int getAlpha() {
    return alpha;
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  public Color getThisObj()
  {
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof Color)) return false;

    Color color = (Color) o;

    return new EqualsBuilder()
        .append(red, color.red)
        .append(green, color.green)
        .append(blue, color.blue)
        .append(alpha, color.alpha)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(red)
        .append(green)
        .append(blue)
        .append(alpha)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("red", red)
        .append("green", green)
        .append("blue", blue)
        .append("alpha", alpha)
        .toString();
  }
}
