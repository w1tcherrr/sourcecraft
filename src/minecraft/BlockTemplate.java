package minecraft;

import basic.NameSupplier;

import java.util.HashMap;
import java.util.Map;

public class BlockTemplate extends Block {

  private String title;
  private Map<String, String> properties;

  public BlockTemplate() {
  }

  public BlockTemplate addProperty(String property, String value) {
    return this.addProperty(() -> property, () -> value);
  }

  public BlockTemplate addProperty(NameSupplier property, NameSupplier value) {
    if (this.properties == null) {
      this.properties = new HashMap<String, String>();
    }
    this.properties.put(property.getName(), value.getName());
    return this;
  }

  public BlockTemplate copyFrom(Block template) {
    this.title = template.getName();
    this.properties = template.getProperties();
    return this;
  }

  public BlockTemplate reset() {
    this.title = null;
    this.properties = null;
    return this;
  }

  @Override
  public String getName() {
    return this.title;
  }

  public BlockTemplate setName(Material material) {
    this.title = material.getName();
    return this;
  }

  public BlockTemplate setName(String title) {
    this.title = title;
    return this;
  }

  @Override
  public Map<String, String> getProperties() {
    return this.properties;
  }

  public BlockTemplate setProperties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }
}
