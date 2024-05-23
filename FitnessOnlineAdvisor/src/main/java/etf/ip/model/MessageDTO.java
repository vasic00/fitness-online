package etf.ip.model;

public class MessageDTO {
	
	private long id;
    private String content;
    private boolean unread;
    private long senderId;
    private long receiverId;
    private String sender;
    private String timeOfSending;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isUnread() {
		return unread;
	}
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTimeOfSending() {
		return timeOfSending;
	}
	public void setTimeOfSending(String timeOfSending) {
		this.timeOfSending = timeOfSending;
	}
	
    
}
