package org.room.apollo.server.dto.soundcloud;

/**
 * Created by Alexey on 10/5/17.
 */
public class PlaylistSC extends BaseSoundType {
    private String ean;
    private Type type;

    public enum Type {
        EP_SINGLE, ALBUM, COMPILATION, PROJECT_FILES, SHOWCASE, DEMO, SAMPLE_PACK, OTHER
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
