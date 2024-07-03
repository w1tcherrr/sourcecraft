package periphery;

public class TextureOptions {

    private static final int DEFAULT_SCALE = 128;

    private int scale = DEFAULT_SCALE;
    private int textureVersion;

    public TextureOptions() {
        this.textureVersion = -1;
    }

    public static boolean isUpToDate(TextureOptions one, TextureOptions two) {
        if (one == null) {
            return false;
        }
        if (two == null) {
            return true;
        }
        return one.getTextureVersion() >= two.getTextureVersion();
    }

    public int getTextureVersion() {
        try {
            return Integer.valueOf(this.textureVersion);
        } catch (java.lang.NumberFormatException e) {
            return 0;
        }
    }

    public void setTextureVersion(int textureVersion) {
        this.textureVersion = textureVersion;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
