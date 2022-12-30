package entities;

import java.io.Serializable;

public class ForbiddenStuff implements Serializable {
    private String id;
    private String stuff;
    private String punishment;

    public ForbiddenStuff(String id, String stuff, String punishment) {
        this.id = id;
        this.stuff = stuff;
        this.punishment = punishment;
    }

    public String getId() {
        return id;
    }

    public String getStuff() {
        return stuff;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }
}
