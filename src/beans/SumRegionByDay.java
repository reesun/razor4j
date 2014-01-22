package beans;

public class SumRegionByDay {
	private String datetime;
	private String region;
	private String city;
	private Integer num;
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String date_time){
		this.datetime = date_time;
	}
	
	public String getRegion(){
		return region;
	}
	public void setRegion(String region){
		this.region = region;
	}
	
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city = city;
	}
	
	public Integer getNum(){
		return num;
	}
	public void setNum(Integer num){
		this.num = num;
	}
}
