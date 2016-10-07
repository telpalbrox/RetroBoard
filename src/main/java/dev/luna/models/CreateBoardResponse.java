package dev.luna.models;

import org.bson.types.ObjectId;

/**
 * Created by alberto on 06/10/16.
 */
public class CreateBoardResponse {
    private long id;

    public CreateBoardResponse(long id) {
     this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
