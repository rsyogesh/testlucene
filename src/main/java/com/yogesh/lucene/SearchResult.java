package com.yogesh.lucene;

import java.io.Serializable;

public class SearchResult implements Serializable {
 //   private String[] fragments;
	private String setpath;
	private int size;
    public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSetpath() {
		return setpath;
	}
	public void setSetpath(String setpath) {
		this.setpath = setpath;
	}
	private String filename;
   /* public String[] getFragments() {
            return fragments;
    }
    public void setFragments(String[] fragments) {
            this.fragments = fragments;
    }*/
    public String getFilename() {
            return filename;
    }
    public void setFilename(String filename) {
            this.filename = filename;
    }
}
