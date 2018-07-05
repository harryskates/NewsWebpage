package server.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Message {
    private int id;
    private String msgText;
    private String author;
    private String postDate;

    public static ArrayList<Message>messages = new ArrayList<Message>();

    public Message(int id, String msgText, String author, String postDate) {
        this.id = id;
        this.msgText = msgText;
        this.author = author;
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msgText='" + msgText + '\'' +
                ", author='" + author + '\'' +
                ", postDate='" + postDate + '\'' +
                '}';
    }


    public static int nextId() {
        int id = 0;
        for (Message m : messages) {
            if (m.getId() > id) {
                id = m.getId();
            }
        }
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("id", getId());
        j.put("msgText", getMsgText());
        j.put("postDate", getPostDate());
        j.put("author", getAuthor());


        return j;
    }

}