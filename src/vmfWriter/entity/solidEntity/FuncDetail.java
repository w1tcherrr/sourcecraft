package vmfWriter.entity.solidEntity;

import vmfWriter.Solid;
import vmfWriter.ValveWriter;

import java.io.IOException;

/**
 *
 */
public class FuncDetail extends SolidEntity {

  public FuncDetail(Solid solid) {
    super(solid);
  }

  public FuncDetail(Solid[] solids) {
    super(solids);
  }

  @Override
  public String getName() {
    return "func_detail";
  }

  @Override
  public void writeVmfSpecific(ValveWriter writer) throws IOException {
    // nothing specific
  }
}
