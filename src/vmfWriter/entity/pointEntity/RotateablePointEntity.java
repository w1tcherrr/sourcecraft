package vmfWriter.entity.pointEntity;

import minecraft.Position;
import vmfWriter.ValveWriter;

import java.io.IOException;

public class RotateablePointEntity extends PointEntity {

//	private int rotation;

  protected Position angle = new Position();

  public RotateablePointEntity() {

  }

  @Override
  public PointEntity create(Position origin) {
    RotateablePointEntity result = new RotateablePointEntity();
    result.setName(this.getName());
    result.setOrigin(origin);
    result.setAngle(this.angle);
    return result;
  }

  public int getRotation() {
    return this.angle.getX();
  }

  public RotateablePointEntity setRotation(int rotation) {
    this.angle.setY(rotation);
    return this;
  }

  public RotateablePointEntity setRotation(Position rotation) {
    this.angle.setTo(rotation);
    return this;
  }

  protected Position getAngle() {
    return this.angle;
  }

  public RotateablePointEntity setAngle(Position angle) {
    this.angle = angle.copy();
    return this;
  }

  @Override
  public final void writeVmfSpecific(ValveWriter writer) throws IOException {
    writer.put("angles", this.angle);
//		writer.put("angles", "0 " + this.getRotation() + " 0");
    this.writeVmfSpecific2(writer);
  }

  public void writeVmfSpecific2(ValveWriter writer) throws IOException {

  }

  @Override
  public RotateablePointEntity setName(String name) {
    this.name = name;
    return this;
  }

}
