package de.vanfanel.utils;

public class Dimension implements Cloneable{
  private int width;

  private int height;

  public Dimension() {
  }

  public Dimension(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Dimension clone() {
    try {
      return (Dimension) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return new Dimension(width,height);
    }
  }
}
