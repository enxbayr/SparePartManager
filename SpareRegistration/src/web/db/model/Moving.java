package web.db.model;

public class Moving {

	private int id;
	private int spare_id;
	private String c_date;
	private String user_name;
	private String comment;
	private int loc_from;
	private int loc_to;
	private int dep_id;
	private int status_from_id;
	private int status_to_id;
	private String spareName;
	private String serialKey;
	private String locFrom;
	private String locTo;
	private String statFrom;
	private String statTo;
	private String depName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSpare_id() {
		return spare_id;
	}

	public void setSpare_id(int spare_id) {
		this.spare_id = spare_id;
	}

	public String getC_date() {
		return c_date;
	}

	public void setC_date(String c_date) {
		this.c_date = c_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLoc_from() {
		return loc_from;
	}

	public void setLoc_from(int loc_from) {
		this.loc_from = loc_from;
	}

	public int getLoc_to() {
		return loc_to;
	}

	public void setLoc_to(int loc_to) {
		this.loc_to = loc_to;
	}

	public int getDep_id() {
		return dep_id;
	}

	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}

	public int getStatus_from_id() {
		return status_from_id;
	}

	public void setStatus_from_id(int status_from_id) {
		this.status_from_id = status_from_id;
	}

	public int getStatus_to_id() {
		return status_to_id;
	}

	public void setStatus_to_id(int status_to_id) {
		this.status_to_id = status_to_id;
	}

	public String getSpareName() {
		return spareName;
	}

	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}

	public String getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	public String getLocFrom() {
		return locFrom;
	}

	public void setLocFrom(String locFrom) {
		this.locFrom = locFrom;
	}

	public String getLocTo() {
		return locTo;
	}

	public void setLocTo(String locTo) {
		this.locTo = locTo;
	}

	public String getStatFrom() {
		return statFrom;
	}

	public void setStatFrom(String statFrom) {
		this.statFrom = statFrom;
	}

	public String getStatTo() {
		return statTo;
	}

	public void setStatTo(String statTo) {
		this.statTo = statTo;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
}