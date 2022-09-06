package periphery;

import vmfWriter.Color;

import java.util.Stack;

public class ConvertOption {

  private String name;
  private int scale;
  private String skyTexture;
  private Color sunLight;
  private Color sunAmbient;
  private Color sunShadow;
  private Stack<String> addables;

  public ConvertOption() {
    this.addables = new Stack<String>();
  }

  public ConvertOption(String aName) {
    this.addables = new Stack<String>();
    this.name = aName;
    this.scale = 40;
  }

  public static ConvertOption create() {
    return new ConvertOption();
  }

  public String getName() {
    return this.name;
  }

  public ConvertOption setName(String name) {
    this.name = name;
    return this;
  }

  public int getScale() {
    return this.scale;
  }

  public ConvertOption setScale(int scale) {
    this.scale = scale;
    return this;
  }

  public String getSkyTexture() {
    return this.skyTexture;
  }

  public ConvertOption setSkyTexture(String skyTexture) {
    this.skyTexture = skyTexture;
    return this;
  }

  public Color getSunAmbient() {
    return this.sunAmbient;
  }

  public ConvertOption setSunAmbient(Color sunAmbient) {
    this.sunAmbient = sunAmbient;
    return this;
  }

  public Color getSunLight() {
    return this.sunLight;
  }

  public ConvertOption setSunLight(Color sunLight) {
    this.sunLight = sunLight;
    return this;
  }

  public Color getSunShadow() {
    return this.sunShadow;
  }

  public ConvertOption setSunShadow(Color sunShadow) {
    this.sunShadow = sunShadow;
    return this;
  }

  public ConvertOption addAddable(String addable) {
    this.addables.push(addable);
    return this;
  }

  public String[] getAddablesAsStrings() {
    Stack<String> addablesNew = new Stack<String>();
    int l = this.addables.size();
    String[] result = new String[l];
    for (int i = 0; i < l; i++) {
      result[i] = this.addables.pop();
      addablesNew.push(result[i]);
    }
    this.addables = addablesNew;
    return result;
  }
}
