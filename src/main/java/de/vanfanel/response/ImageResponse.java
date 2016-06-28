package de.vanfanel.response;

import de.vanfanel.utils.Color;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
