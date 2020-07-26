package web.db.model;

import java.util.List;

public class SpareCount {

	private Spare sp;
	private List<Status> st;

	public Spare getSp() {
		return sp;
	}

	public void setSp(Spare sp) {
		this.sp = sp;
	}

	public List<Status> getSt() {
		return st;
	}

	public void setSt(List<Status> st) {
		this.st = st;
	}

}