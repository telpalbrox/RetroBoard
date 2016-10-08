package dev.luna.models;

import org.bson.types.ObjectId;

/**
 * Created by alberto on 06/10/16.
 */
public class CreateBoardResponse {
    private String uuid;

    public CreateBoardResponse(String uuid) {
     this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
