package utils.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class DateSKHandler implements ResultSetHandler<Integer> {
	@Override
	public Integer handle(ResultSet rs) throws SQLException {
		if (!rs.next())
			return -1;
		
		return rs.getInt(1);				
	}
}
