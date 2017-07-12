package example.com.sunshine.download.Http.entity;

import java.io.Serializable;

public class PlayUrlItem  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1969535441345914017L;
	private int id;
	private String url;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
