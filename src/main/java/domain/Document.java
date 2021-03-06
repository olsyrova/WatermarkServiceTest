package domain;

import java.util.UUID;

/**
 * Created by syrovo01 on 26.08.2016.
 */
public class Document {

	private String title;
	private String author;
	private Watermark watermark;

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Watermark getWatermark() {
		return watermark;
	}

	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Document(String title, String author){
            this.title = title;
            this.author = author;
            this.watermark = null;

	}

}
