package vmfWriter.entity.pointEntity;

import minecraft.Position;
import vmfWriter.ValveWriter;
import vmfWriter.entity.Entity;

import java.io.IOException;

public abstract class PointEntity extends Entity {

    protected Position origin;

    public PointEntity() {
        this.origin = new Position();
    }

    public PointEntity(Position origin) {
        this.origin = new Position(origin);
    }

    protected void setOrigin(Position origin) {
        this.origin = new Position(origin);
    }

    public abstract PointEntity create(Position origin);

    @Override
    protected void writeEnd(ValveWriter writer) throws IOException {
        writer.put(Entity.ORIGIN_TAG, this.origin.getString());
        super.writeEnd(writer);
    }
}
