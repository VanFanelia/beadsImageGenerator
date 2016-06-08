package de.vanfanel.response;

public class ImageData {

  private String imgBase64 = "";

  private int height = 0;

  private int width = 0;

  private int[] pixelValues;

  public ImageData() {
  }

  public String getImgBase64() {
    return imgBase64;
  }

  public void setImgBase64(String imgBase64) {
    this.imgBase64 = imgBase64;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int[] getPixelValues() {
    return pixelValues;
  }

  public void setPixelValues(int[] pixelValues) {
    this.pixelValues = pixelValues;
  }
}
