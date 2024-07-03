package nbtReader;

import converter.Converter;
import minecraft.Block;
import minecraft.Blocks;

import java.io.DataInputStream;
import java.io.IOException;

public class ChunkReader extends NbtReader {

    private static final NamedTag Y = new NamedTag(NbtTag.BYTE, "Y");

    private static final NamedTag SECTIONS_OLD = new NamedTag(NbtTag.LIST, "Sections");
    private static final NamedTag SECTIONS_NEW = new NamedTag(NbtTag.LIST, "sections");

    private static final NamedTag PALETTE_OLD = new NamedTag(NbtTag.LIST, "Palette");
    private static final NamedTag PALETTE_NEW = new NamedTag(NbtTag.LIST, "palette");

    private static final NamedTag BLOCK_STATES_OLD = new NamedTag(NbtTag.LONG_ARRAY, "BlockStates");
    private static final NamedTag BLOCK_STATES_NEW = new NamedTag(NbtTag.LONG_ARRAY, "data");

    private static final NamedTag PALETTE_NAME = new NamedTag(NbtTag.STRING, "Name");
    private static final NamedTag PALETTE_PROPERTIES = new NamedTag(NbtTag.COMPOUND, "Properties");

    private static final NamedTag LEVEL = new NamedTag(NbtTag.COMPOUND, "Level");

    private final Converter map;
    private final WorldPiece convertee;
    private final boolean isBefore1_18;

    public ChunkReader(DataInputStream stream, Converter map, WorldPiece convertee, int worldVersion) {
        super(stream);
        this.map = map;
        this.convertee = convertee;
        this.isBefore1_18 = worldVersion <= 2730; // https://minecraft.fandom.com/wiki/Data_version
    }

    public void readChunk() throws IOException {
        if (this.readTag() == NbtTag.COMPOUND) {
            this.readTitle();

            if (isBefore1_18) {
                NbtTasks levelTask = NbtTasks.INSTANCE.create()
                        .put(SECTIONS_OLD, this::readSections);

                NbtTasks compoundTask = NbtTasks.INSTANCE.create()
                        .put(LEVEL, () -> this.doCompound(levelTask));

                this.doCompound(compoundTask);
            } else {
                NbtTasks levelTask = NbtTasks.INSTANCE.create()
                        .put(SECTIONS_NEW, this::readSections);

                this.doCompound(levelTask);
            }
        }
    }

    private void readSections() throws IOException {
        this.doListOfCompounds(() -> {
            Section section = this.readSection();
            this.map.addMcaSection(section);
        });
    }

    /**
     * Reads the section after the title has already be been read.
     */
    private Section readSection() throws IOException {
        Section section = new Section(this.convertee);
        if (isBefore1_18) {
            this.doCompound(NbtTasks.INSTANCE.create()
                    .put(Y, () -> section.setHeight(this.readByte()))
                    .put(BLOCK_STATES_OLD, () -> section.readBlocksRaw(this))
                    .put(PALETTE_OLD, () -> this.readPalette(section)));
        } else {
            this.doCompound(NbtTasks.INSTANCE.create()
                    .put(Y, () -> section.setHeight(this.readByte()))
                    .put(BLOCK_STATES_NEW, () -> section.readBlocksRaw(this))
                    .put(PALETTE_NEW, () -> this.readPalette(section)));
        }
        return section;
    }

    private void readPalette(Section section) throws IOException {
        this.doListOfCompounds(pos -> section.addPalette(pos, this.readBlock()));
    }

    private Block readBlock() throws IOException {
        return Blocks.I.getIO(template -> this.doCompound(NbtTasks.INSTANCE.create()
                .put(PALETTE_NAME, () -> template.setName(this.readString()))
                .put(PALETTE_PROPERTIES,
                        () -> this.doCompound(title -> template.addProperty(title, this.readString())))));
    }
}
