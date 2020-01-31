package api.main;

import java.util.List;

/*
 * This class should enable parsing response from open search query
 * To a usable class
 * 
 * This should be made using JSON parsing exrternal library
 * If I would properly prepared (look in SearchForClubs.java line:13 comment)
 * I would use GSON or Jackson 
 */

public class ResponseWrapperOpenSearchQuery {

	private String searchParam; 
	private List<String> titles;
	private List<String> emptyList;
	private List<String> adresses;
	
	public String getSearchParam() {
		return searchParam;
	}
	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<String> getEmptyList() {
		return emptyList;
	}
	public void setEmptyList(List<String> emptyList) {
		this.emptyList = emptyList;
	}
	public List<String> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<String> adresses) {
		this.adresses = adresses;
	}
	public ResponseWrapperOpenSearchQuery(String searchParam, List<String> titles, List<String> emptyList, List<String> adresses) {
		this.searchParam = searchParam;
		this.titles = titles;
		this.emptyList = emptyList;
		this.adresses = adresses;
	}
	
	public ResponseWrapperOpenSearchQuery() {
	}
	
	
	
}
