package com.define.commons.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
 
@Entity(name="actself_leavebill")
public class LeaveBill {

    private int id;
	private String days;
	private String content;
	private String remark;
	private Date leavedate;
	private int state;	//0-初始录入   1-开始审批     2-审批完成
	private String user_id;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getLeavedate() {
		return leavedate;
	}
	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public LeaveBill() {
		super();
	}
	public LeaveBill(int id, String days, String content, String remark, Date leavedate, int state, String user_id) {
		super();
		this.id = id;
		this.days = days;
		this.content = content;
		this.remark = remark;
		this.leavedate = leavedate;
		this.state = state;
		this.user_id = user_id;
	}
	public LeaveBill(String days, String content, String remark, Date leavedate, int state, String user_id) {
		super();
		this.days = days;
		this.content = content;
		this.remark = remark;
		this.leavedate = leavedate;
		this.state = state;
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "LeaveBill [id=" + id + ", days=" + days + ", content=" + content + ", remark=" + remark + ", leavedate="
				+ leavedate + ", state=" + state + ", user_id=" + user_id + "]";
	}
}
