package dev.luna;

/**
 * Created by alberto on 05/10/16.
 */
public class ListAction {
    private String type;
    private String payload;

    public ListAction() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
