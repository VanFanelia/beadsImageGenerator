package de.vanfanel.request;

import de.vanfanel.utils.ColorType;
import java.util.ArrayList;
import java.util.List;

public class ImageRequest {

  private String link;

  private List<ColorType> beadGroups = new ArrayList<>();

  public ImageRequest() {
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public List<ColorType> getBeadGroups() {
    return beadGroups;
  }

  public void setBeadGroups(List<ColorType> beadGroups) {
    this.beadGroups = beadGroups;
  }
}
