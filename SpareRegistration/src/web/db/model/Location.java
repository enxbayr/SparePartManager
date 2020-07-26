package web.db.model;

public class Location {

	private int id;
	private String c_building;
	private String c_room;
	private String c_rack;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getC_building() {
		return c_building;
	}
	public void setC_building(String c_building) {
		this.c_building = c_building;
	}
	public String getC_room() {
		return c_room;
	}
	public void setC_room(String c_room) {
		this.c_room = c_room;
	}
	public String getC_rack() {
		return c_rack;
	}
	public void setC_rack(String c_rack) {
		this.c_rack = c_rack;
	}
	
}