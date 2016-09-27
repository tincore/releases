package com.tincore.auth.server.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ArrayELResolver;

public class PageForm<T> implements Iterable<T> {
    
	private List<T> content;
    private long number;
    private long size;
    private long totalElements;
    private long totalPages;
    
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	
	@Override
	public Iterator<T> iterator() {
		return getContent().iterator();
	}
	
	public void add(T entity) {
		if (content == null){
			content = new ArrayList<>();
		}
		content.add(entity);
	}
	
	

}