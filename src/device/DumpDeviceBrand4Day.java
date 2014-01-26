package device;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.DBHandler;
import utils.handler.DataIDHandler;
import beans.DeviceBrand;

public class DumpDeviceBrand4Day {

	public static void main(String[] args) throws Exception {
		Connection conn = DBHandler.getConnection();
		if (conn == null)
			return;

		ResultSetHandler<Integer> dataidHandler = new DataIDHandler();
		QueryRunner run = new QueryRunner();
		
		// 从fact_devicebrand中查看最大的dataid
		String query = "SELECT MAX( dataid ) FROM razor_fact_devicebrand";
		
		Integer maxDataID = run.query(conn, query, dataidHandler);
		if(maxDataID == -1)
			return ;

		// 从 fact_clientdata中，dataid+1开始dump数据。
		// 字段为 dataid, product_sk, devicebrand_sk,date_sk,deviceidentifier,is_new,isnew_channel
		
		query = "SELECT dataid, devicebrand_sk, date_sk, deviceidentifier, product_sk, isnew, isnew_channel "
				+ " FROM razor_fact_clientdata WHERE dataid > ?";

//		System.out.println(query);

//		ResultSetHandler<List<DeviceBrand>> deviceHandler = new BeanListHandler<DeviceBrand>(DeviceBrand.class);
		List<DeviceBrand> deviceBrands = run.query(conn, query, new BeanListHandler<DeviceBrand>(DeviceBrand.class),maxDataID);

		query = "insert into razor_fact_devicebrand "
				+ " (dataid,product_sk,devicebrand_sk,date_sk,deviceidentifier,isnew,isnew_channel) "
				+ " values (?,?,?,?,?,?,?)";
		for (DeviceBrand deviceBrand : deviceBrands) {
			// System.out.println(region.getDatetime()+" "+region.getRegion()+" "+ region.getCity()+ " " + region.getNum());
			run.update(conn, query, 
					deviceBrand.getDataid(), deviceBrand.getProduct_sk(), 
					deviceBrand.getDevicebrand_sk(),deviceBrand.getDate_sk(), 
					deviceBrand.getDeviceidentifier(),
					deviceBrand.getIsnew(),	deviceBrand.getIsnew_channel());
		}

		System.out.println(maxDataID + " down!");
	}

}
