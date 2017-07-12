package example.com.sunshine.download.Http.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by hywel on 2016/12/22.
 */

public abstract class ProgramInfoEntity implements MultiItemEntity, Serializable {
    private int itemType;
    private int spanSize;
    public static final int PROGRAM_TYPE_THEATRE = 2;
    public static final int PROGRAM_TYPE_ARTIST = 1;
    public static final int PROGRAM_TYPE_BANNER = 0;


    public ProgramInfoEntity() {
    }

    public ProgramInfoEntity(int itemType) {
        this.itemType = itemType;
    }

    public ProgramInfoEntity(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    @Override
    public int getItemType() {
        return getRealItemType();
    }

    public abstract int getRealItemType();

}
