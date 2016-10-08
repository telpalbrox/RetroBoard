package dev.luna.models;

/**
 * Created by alberto on 08/10/16.
 */
public class SocketResponse {
    private SocketResponse.Type type;
    private Object payload;
    private String entity;

    public SocketResponse() {

    }

    public SocketResponse(SocketResponse.Type type, String entity, Object payload) {
        this.type = type;
        this.payload = payload;
        this.entity = entity;
    }

    public SocketResponse(SocketResponse.Type type, String entity) {
        this(type, entity, null);
    }

    public SocketResponse.Type getType() {
        return type;
    }

    public void setType(SocketResponse.Type type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public enum Type {
        ADDED, REMOVED;
    }
}
