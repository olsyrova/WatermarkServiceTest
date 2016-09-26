package domain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
/**
 * Created by syrovo01 on 26.08.2016.
 */
public class Watermark {
    Gson gson = new Gson();
	private String content;
	private String title;
	private String author;
	private Topic topic;

	public Watermark(String content, String title, String author, Topic topic){
		this.content = content;
		this.title = title;
		this.author = author;
		this.topic = topic;
	}

	public Watermark(String content, String title, String author){
		this.content = content;
		this.title = title;
		this.author = author;
	}

	public String toString(){
        JsonObject obj = new JsonObject();
        obj.addProperty("content", this.content);
        obj.addProperty("title", this.title);
        obj.addProperty("author", this.author);
        if (this.topic != null){
            obj.addProperty("topic", topic.toString());
        }
        return obj.toString();

	}

}
