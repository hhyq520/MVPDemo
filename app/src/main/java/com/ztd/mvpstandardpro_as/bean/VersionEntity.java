package com.ztd.mvpstandardpro_as.bean;

/**
 * Created by Administrator on 2018/6/13.
 */

public class VersionEntity {
	private String version;
	private String url;
	private int fromSource;
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFromSource() {
		return fromSource;
	}

	public void setFromSource(int fromSource) {
		this.fromSource = fromSource;
	}
}
