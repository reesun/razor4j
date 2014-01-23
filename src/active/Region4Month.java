package active;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.DBHandler;
import utils.DateUtils;
import utils.handler.DateSKHandler;
import beans.SumRegionByDay;

public class Region4Month {
	public static void main(String[] args) throws Exception {
		Connection conn = DBHandler.getConnection();
		if (conn == null)
			return;

		ResultSetHandler<Integer> dateHandler = new DateSKHandler();

		String query = "SELECT date_sk FROM  razor_dim_date WHERE datevalue=?";
		QueryRunner run = new QueryRunner();

		String dateValue = DateUtils.getLastMonth();
		Integer datesk = run.query(conn, query, dateHandler, dateValue+"-01");
		Integer endDatesk = datesk + 30;

		query = "SELECT '"
				+ dateValue
				+ "' as datetime, region, city, count(distinct(deviceidentifier)) as num "
				+ " FROM razor_fact_clientdata f join  razor_dim_location  l"
				+ " on f.location_sk = l.location_sk" + " where date_sk >= ? and date_sk <= ?"
				+ " group by f.location_sk";

		// System.out.println(query);

		ResultSetHandler<List<SumRegionByDay>> regionHandler = new BeanListHandler<SumRegionByDay>(
				SumRegionByDay.class);
		List<SumRegionByDay> regions = run.query(conn, query, regionHandler,datesk, endDatesk);

		query = "insert into razor_sum_region_by_month (datetime,region,city,num) values (?,?,?,?)";

		for (SumRegionByDay region : regions) {
			// System.out.println(region.getDatetime()+" "+region.getRegion()+" "+
			// region.getCity()+ " " + region.getNum());
			run.update(conn, query, region.getDatetime(), region.getRegion(),
					region.getCity(), region.getNum());
		}

		System.out.println(dateValue + " down!");
	}
}
