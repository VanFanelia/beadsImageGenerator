package de.vanfanel.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class HAMAColor extends Color{

  private String id;

  private String name;

  public HAMAColor() {
  }

  public HAMAColor(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public HAMAColor(String id, String name, int red, int green, int blue, int alpha) {
    super(red, green, blue, alpha);
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HAMAColor getThisObj()
  {
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof HAMAColor)) return false;

    HAMAColor hamaColor = (HAMAColor) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(id, hamaColor.id)
        .append(name, hamaColor.name)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(name)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .toString();
  }
}
