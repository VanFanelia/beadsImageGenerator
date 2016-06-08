package de.vanfanel.response;

import java.util.ArrayList;
import java.util.List;

public class ImageResponse {

  private List<ImageData> images = new ArrayList<>();

  public ImageResponse() {
  }

  public ImageResponse(List<ImageData> images) {
    this.images = images;
  }

  public List<ImageData> getImages() {
    return images;
  }

  public void setImages(List<ImageData> images) {
    this.images = images;
  }
}
