package dev.luna.models;

/**
 * Created by alberto on 06/10/16.
 */
public class CreateBoardResponse {
    private String id;

    public CreateBoardResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
