package beans;

public class DeviceBrand {
	private Integer dataid, product_sk, devicebrand_sk, date_sk, isnew, isnew_channel;
	private String deviceidentifier;
	
	public Integer getDataid(){
		return dataid;
	}
	public void seteDataid(Integer dataid){
		this.dataid = dataid;
	}
	
	public Integer getProduct_sk(){
		return product_sk;
	}
	public void seteProduct_sk(Integer product_sk){
		this.product_sk = product_sk;
	}
	
	public Integer getDevicebrand_sk(){
		return devicebrand_sk;
	}
	public void seteDevicebrand_sk(Integer devicebrand_sk){
		this.devicebrand_sk = devicebrand_sk;
	}
	
	public Integer getDate_sk(){
		return date_sk;
	}
	public void seteDate_sk(Integer date_sk){
		this.date_sk = date_sk;
	}
	
	public Integer getIsnew(){
		return isnew;
	}
	public void setIsnew(Integer isnew){
		this.isnew = isnew;
	}
	
	public Integer getIsnew_channel(){
		return isnew_channel;
	}
	public void setIsnew_channel(Integer isnew_channel){
		this.isnew_channel = isnew_channel;
	}
	
	public String getDeviceidentifier(){
		return deviceidentifier;
	}
	public void setDeviceidentifier(String deviceidentifier){
		this.deviceidentifier = deviceidentifier;
	}
	
	
}
