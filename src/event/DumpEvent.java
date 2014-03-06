package event;

import java.sql.Connection;

public class DumpEvent {
	/*
	 * 基本思路：
	 * 定时从pre_phone_user_event导出对应app_id以及app_name的数据
	 * 保存到event_hook中
	 */
	public static void main(String[] args) throws Exception {
		Connection dashreportConn = utils.DBHandler.getConnection();
		Connection snswebbusConn = utils.DBHandler.getConnection("res/db_snswebbus.properties");
		
		if(null == dashreportConn || null == snswebbusConn)
			return;
		
	}

}
