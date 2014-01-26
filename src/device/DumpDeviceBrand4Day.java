package device;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.DBHandler;
import utils.DateUtils;
import utils.handler.DateSKHandler;
import beans.SumRegionByDay;

public class DumpDeviceBrand4Day {

	public static void main(String[] args) throws Exception {
		Connection conn = DBHandler.getConnection();
		if (conn == null)
			return;

		ResultSetHandler<Integer> dateHandler = new DateSKHandler();
		QueryRunner run = new QueryRunner();
		
		// 从fact_devicebrand中查看最大的dataid
		String query = "SELECT MAX( dataid ) FROM razor_fact_devicebrand";
		
		Integer maxDataID = run.query(conn, query, dateHandler);

		// 从 fact_clientdata中，dataid+1开始dump数据。
		// 字段为 dataid, product_sk, devicebrand_sk,date_sk,deviceidentifier,is_new,isnew_channel
		
		query = "SELECT dataid, product_sk, devicebrand_sk, date_sk, deviceidentifier, isnew, isnew_channel "
				+ " FROM razor_fact_clientdata WHERE dataid > ?";

//		System.out.println(query);

		ResultSetHandler<List<SumRegionByDay>> regionHandler = new BeanListHandler<SumRegionByDay>(
				SumRegionByDay.class);
		List<SumRegionByDay> regions = run.query(conn, query, regionHandler,maxDataID);

		query = "insert into razor_fact_devicebrand (datetime,region,city,num) values (?,?,?,?)";

		for (SumRegionByDay region : regions) {
			// System.out.println(region.getDatetime()+" "+region.getRegion()+" "+ region.getCity()+ " " + region.getNum());
			run.update(conn, query, region.getDatetime(), region.getRegion(),
					region.getCity(), region.getNum());
		}

		System.out.println(maxDataID + " down!");
	}

}
