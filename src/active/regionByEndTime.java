package active;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import beans.RegionBasic;

public class regionByEndTime {
	
	public static void main(String[] args) throws Exception {
			Connection conn = utils.DBHandler.getConnection();
			if(null == conn)
				return;
			
			QueryRunner run = new QueryRunner();
			
			ResultSetHandler<List<RegionBasic>> regionHandler = new BeanListHandler<RegionBasic>(RegionBasic.class);
			
			String sql = "select  '2014-02-17' as enddate, if(l.region<>'', l.region, '其他') region,count(distinct f.deviceidentifier) as week_active"
					+ "	from    razor_fact_clientdata     f,"
					+ "			razor_dim_date      d,"
					+ "			razor_dim_product      p,"
					+ "			razor_dim_location     l"
					+ "	where    l.country = '中国'"
					+ "		and f.date_sk = d.date_sk"
					+ "		and f.product_sk = p.product_sk"
					+ "		and f.location_sk = l.location_sk"
					+ "		and d.datevalue between '2014-02-11' and '2014-02-17'"
					+ "		and p.product_id = 1"
					+ "	group by l.region"
					+ "	order by week_active desc";
			
			List<RegionBasic> regions = run.query(conn, sql, regionHandler);
			for(RegionBasic region:regions){
				System.out.println(region.toString());
			}
	} 

}
