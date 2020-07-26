package web.db.model;

public class Systems {

	private int id;
	private String c_name;
	private String c_vendor;
	private String c_desc;
	private int dep_id;
	private String depName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_vendor() {
		return c_vendor;
	}

	public void setC_vendor(String c_vendor) {
		this.c_vendor = c_vendor;
	}

	public String getC_desc() {
		return c_desc;
	}

	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
	
	public int getDep_id() {
		return dep_id;
	}

	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}
	
	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

}