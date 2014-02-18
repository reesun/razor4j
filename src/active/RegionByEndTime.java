package active;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import beans.RegionBasic;

public class RegionByEndTime {
	
	public static void main(String[] args) throws Exception {
			Connection conn = utils.DBHandler.getConnection();
			if(null == conn)
				return;
			
			QueryRunner run = new QueryRunner();
			String fromWeek = utils.DateUtils.getLastWeek();
			String endDate = utils.DateUtils.getCurr();
			String fromMonth = utils.DateUtils.getBeforeDays(-30);
			System.out.println(fromWeek + " " + fromMonth +" "+endDate);
			
			ResultSetHandler<List<RegionBasic>> regionHandler = new BeanListHandler<RegionBasic>(RegionBasic.class);
			
			String sql = "select  '"+endDate+"' as enddate, if(l.region<>'', l.region, '其他') region,"
					+ "		count(distinct f.deviceidentifier) as week_active, sum(isnew) week_new, p.product_id productid"
					+ "	from    razor_fact_clientdata     f,"
					+ "			razor_dim_date      d,"
					+ "			razor_dim_product      p,"
					+ "			razor_dim_location     l"
					+ "	where    l.country = '中国'"
					+ "		and f.date_sk = d.date_sk"
					+ "		and f.product_sk = p.product_sk"
					+ "		and f.location_sk = l.location_sk"
					+ "		and d.datevalue between '"+fromWeek+"' and '"+endDate+"'"
					+ "	group by p.product_id, l.region";
			
			List<RegionBasic> weekRegions = run.query(conn, sql, regionHandler);
			
			for(RegionBasic region:weekRegions){
				int productid = region.getProductid();
				String regionName = region.getRegion();
				if("其他".equals(regionName))
					regionName = "";
				
				sql = "select  count(distinct f.deviceidentifier) as month_active, sum(isnew) month_new"
						+ "	from    razor_fact_clientdata     f,"
						+ "			razor_dim_date      d,"
						+ "			razor_dim_product      p,"
						+ "			razor_dim_location     l"
						+ "	where    l.country = '中国'"
						+ "		and f.date_sk = d.date_sk"
						+ "		and f.product_sk = p.product_sk"
						+ "		and f.location_sk = l.location_sk"
						+ "		and d.datevalue between '"+fromMonth+"' and '"+endDate+"'"
						+  "    	and p.product_id = "+productid
						+ "		and l.region = '" + regionName+"'";
				
				@SuppressWarnings("rawtypes")
				ResultSetHandler monthRegionHandler = new BeanHandler<RegionBasic>(RegionBasic.class);
				
				@SuppressWarnings("unchecked")
				RegionBasic monthRegion = run.query(conn, sql, monthRegionHandler);
				
				
				String query = "insert into razor_sum_basic_region (enddate,region,week_active,week_new,month_new,month_active,productid) values (?,?,?,?,?,?,?)";
				run.update(conn, query, region.getEndDate(), region.getRegion(), region.getWeek_active(), region.getWeek_new(),
						monthRegion.getMonth_active(),monthRegion.getMonth_new(), productid);
				
			}
			
			conn.close();
			System.out.println(endDate + " down!");
	} 
	

}
