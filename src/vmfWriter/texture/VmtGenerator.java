package vmfWriter.texture;

import vmfWriter.ValveWriter;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VmtGenerator {
    private static final Color GRASS_COLOR = new Color(102, 255, 51);

    public static void main(String[] args) {
        try {
            File directory = new File("C:\\Users\\Misch\\Downloads\\SourceCraft_2.12\\textures\\minecraft_original");
            for (File subFile : directory.listFiles()) {
                if (!subFile.getName()
                        .endsWith(".vtf")) {
                    continue;
                }
                System.out.println(subFile.getName()
                        .substring(0, subFile.getName()
                                .length() - 4));
                String vtfName = subFile.getName();
                String plainName = vtfName.substring(0, vtfName.lastIndexOf('.'));
                ValveWriter writer = new ValveWriter(new File(directory, plainName + ".vmt"));
                ValveTexture texture = new ValveTexture("minecraft_original" + "/" + plainName);
                if (plainName.equals("mob_spawner")) {
                    texture.setTranslucent(true);
                } else if (plainName.startsWith("leaves")) {
                    texture.setTranslucent(true);
                    texture.setColor(GRASS_COLOR);
                } else if (plainName.equals("grass_top") || plainName.equals("grass_side_overlay")) {
                    texture.setColor(GRASS_COLOR);
                }
                texture.writeVmf(writer);
                writer.finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
