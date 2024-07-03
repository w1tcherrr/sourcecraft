package nbtReader;

import basic.Tuple;
import minecraft.Area;
import minecraft.Block;
import minecraft.Position;
import periphery.Minecraft;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Section {

    WorldPiece worldPiece;

    private int firstYPosition;

    private long[] blocks;
    private int[][][] rawBlocks = new int[Minecraft.CHUNK_SIZE_X][Minecraft.SECTION_SIZE_Y][Minecraft.CHUNK_SIZE_Z];
    private Map<Integer, Block> palette = new HashMap<Integer, Block>();

    public Section(WorldPiece convertee) {
        this.worldPiece = convertee;
    }

    public WorldPiece getConvertee() {
        return this.worldPiece;
    }

    public Block getBlock(Position p) {
        return this.palette.get(Integer.valueOf(this.rawBlocks[p.x][p.y][p.z]));
    }

    public int getFirstYPosition() {
        return this.firstYPosition;
    }

    public void readBlocksRaw(NbtReader source) throws IOException {
        int length = source.readLength();
        this.blocks = new long[length];
        for (int i = 0; i < length; i++) {
            blocks[i] = source.readLong();
        }
    }

    public void updateBlocks() {
        try {
            int length = blocks.length;
            int unitSize = this.getUnitLength(length);
            BitNbtReader reader = new BitNbtReader(blocks, unitSize);
            for (int y = 0; y < Minecraft.SECTION_SIZE_Y; y++) {
                for (int z = 0; z < Minecraft.CHUNK_SIZE_Z; z++) {
                    for (int x = 0; x < Minecraft.CHUNK_SIZE_X; x++) {
                        this.rawBlocks[x][y][z] = reader.readBits();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getUnitLength(int length) {
        return length / 64;
    }

    public void setHeight(int height) {
        this.firstYPosition = height * Minecraft.SECTION_SIZE_Y;
    }

    @Override
    public String toString() {
        return this.worldPiece.toString() + " @height " + this.firstYPosition;
    }

    public Tuple<Area, Position> getBoundAndTarget() {
        Position lower = this.getConvertee()
                .getLower();
        Position higher = this.getConvertee()
                .getHigher();
        int yMappedToStart = lower.getY();
        int absolutStartY = Math.max(this.getFirstYPosition(), lower.getY());
        int absoluteEndY = Math.min(this.getFirstYPosition() + (Minecraft.SECTION_SIZE_Y - 1), higher.getY());
        Position start = this.getConvertee()
                .getLower();
        start.setY(absolutStartY % Minecraft.SECTION_SIZE_Y);
        Position end = this.getConvertee()
                .getHigher();
        end.setY(absoluteEndY % Minecraft.SECTION_SIZE_Y);
        Position target = Position.subtract(this.getConvertee()
                .getVmfStart(), start);
        target.setY(absolutStartY - start.y - yMappedToStart + 1);
        if (absolutStartY > absoluteEndY) {
            start.y = 1;
            end.y = 0;
        }
        return new Tuple<>(new Area(start, end), target);
    }

    public void addPalette(Integer i, Block block) {
        this.palette.put(i, block);
    }
}
