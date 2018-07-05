package server.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Console;
import server.models.Message;
import server.models.services.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("message/")
public class MessageController {
    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String newMessage(@FormParam("msgText") String msgText,
                             @FormParam("author") String author ) {

        Console.log("/message/new - Posted by " + author);

        MessageService.selectAllInto(Message.messages);
        int messageId = Message.nextId();
        String messageDate = new Date().toString();
        Message newMessage = new Message(messageId, msgText, author, messageDate);
        return MessageService.insert(newMessage);
    }

    @SuppressWarnings("unchecked")
    private String getMessageList() {
        JSONArray messageList = new JSONArray();
        for (Message m : Message.messages) {
            messageList.add(m.toJSON());
        }
        return messageList.toString();
    }
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String listMessages() {
        Console.log("/message/list - Getting all messages from database");
        String status = MessageService.selectAllInto(Message.messages);
        if (status.equals("OK")) {
            return getMessageList();
        } else {
            JSONObject response = new JSONObject();
            response.put("error", status);
            return response.toString();
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteMessage(@FormParam("messageId") int messageId) {
        Console.log("/message/delete - Message " + messageId);
        Message message = MessageService.selectById(messageId);
        if (message == null) {
            return "That message doesn't appear to exist";
        } else {
            return MessageService.deleteById(messageId);
        }
    }

    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String editMessage(@FormParam("messageId") int messageId, @FormParam("messageText") String messageText) {
        Console.log("/message/edit - Message " + messageId);
        Message message = MessageService.selectById(messageId);
        if (message == null) {
            return "That message doesn't appear to exist";
        } else {
            String messageDate = new Date().toString();
            message.setMsgText(messageText);
            message.setPostDate(messageDate);
            return MessageService.update(message);
        }
    }

}

