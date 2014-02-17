package beans;

public class RegionBasic {
	private String enddate;
	private String region;
	
	private Integer week_active;
	private Integer week_new;
	private Integer month_new;
	private Integer month_active;
	private Integer productid;
	
	public String getEndDate(){
		return enddate;
	}
	public void setEndDate(String endDate){
		this.enddate = endDate;
	}
	
	public String getRegion(){
		return region;
	}
	public void setRegion(String region){
		this.region = region;
	}
	
	public Integer getWeek_active() {
		return week_active;
	}
	public void setWeek_active(Integer week_active){
		this.week_active = week_active;
	}
	
	public Integer getWeek_new() {
		return week_new;
	}
	public void setWeek_new(Integer week_new){
		this.week_new = week_new;
	}
	
	public Integer getMonth_active() {
		return month_active;
	}
	public void setMonth_active(Integer month_active){
		this.month_active = month_active;
	}
	
	public Integer getMonth_new() {
		return month_new;
	}
	public void setMonth_new(Integer month_new){
		this.month_new = month_new;
	}
	
	public String toString(){
		return enddate + " " + region + " " + week_active + " " + week_new + " " + month_active + " " + month_new + " " + productid; 
	}
	
	
}
