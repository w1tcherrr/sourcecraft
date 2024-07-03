package nbtReader;

import minecraft.Position;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerInLevelReader extends PlayerPositionReader {

    private static final NamedTag DATA = new NamedTag(NbtTag.COMPOUND, "Data");
    private static final NamedTag PLAYER = new NamedTag(NbtTag.COMPOUND, "Player");
    private static final NamedTag SPAWN_X = new NamedTag(NbtTag.INT, "SpawnX");
    private static final NamedTag SPAWN_Y = new NamedTag(NbtTag.INT, "SpawnY");
    private static final NamedTag SPAWN_Z = new NamedTag(NbtTag.INT, "SpawnZ");
    private static final NamedTag DATA_VERSION = new NamedTag(NbtTag.INT, "DataVersion");
    private final NbtTasks tasks;
    private Position playerPosition = new Position();
    private Position worldSpawn = new Position();
    private int dataVersion;

    public PlayerInLevelReader(DataInputStream stream) {
        super(stream);

        this.tasks = NbtTasks.INSTANCE.create()
                .put(DATA, () -> this.doCompound(NbtTasks.INSTANCE.create()
                        .put(PLAYER, () -> this.playerPosition = this.readPlayerData())
                        .put(SPAWN_X, () -> this.worldSpawn.setX(this.readInt()))
                        .put(SPAWN_Y, () -> this.worldSpawn.setY(this.readInt()))
                        .put(SPAWN_Z, () -> this.worldSpawn.setZ(this.readInt()))
                        .put(DATA_VERSION, () -> this.dataVersion = this.readInt())));
    }

    public PlayerInLevelReader read() throws IOException {
        if (this.readTag() == NbtTag.COMPOUND) {
            this.readTitle();
            this.doCompound(this.tasks);
        }
        return this;
    }

    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public Position getWorldSpawn() {
        return this.worldSpawn;
    }

    public int getDataVersion() {
        return this.dataVersion;
    }
}
