package de.vanfanel.response;

import de.vanfanel.utils.Color;
import java.util.ArrayList;
import java.util.List;

public class ImageResponse {

  private List<ImageData> images = new ArrayList<>();

  private List<Color> knownColors = new ArrayList<>();

  public ImageResponse() {
  }

  public List<ImageData> getImages() {
    return images;
  }

  public void setImages(List<ImageData> images) {
    this.images = images;
  }

  public List<Color> getKnownColors() {
    return knownColors;
  }

  public void setKnownColors(List<Color> knownColors) {
    this.knownColors = knownColors;
  }
}
