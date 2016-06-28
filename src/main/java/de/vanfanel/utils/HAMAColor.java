package de.vanfanel.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import static de.vanfanel.utils.ColorType.HAMA_BASIC;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HAMAColor extends Color{

  private String id;

  private String name;

  public HAMAColor() {
    super();
    super.setType(HAMA_BASIC);
  }

  public HAMAColor(String id, String name, ColorType type, int red, int green, int blue, int alpha) {
    super(red, green, blue, alpha, type);
    this.id = id;
    this.name = name;
  }

  public HAMAColor(String id, String name, int red, int green, int blue, int alpha) {
    super(red, green, blue, alpha, HAMA_BASIC);
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

  @JsonIgnore
  public HAMAColor getThisObj()
  {
    return this;
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
