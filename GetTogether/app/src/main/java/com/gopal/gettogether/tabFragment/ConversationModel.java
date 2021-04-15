package com.gopal.gettogether.tabFragment;

public class ConversationModel {

    public boolean seen;
    public long timestamp;

    public ConversationModel(){

    }

    public ConversationModel(boolean seen, long time) {
        this.seen = seen;
        this.timestamp = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
