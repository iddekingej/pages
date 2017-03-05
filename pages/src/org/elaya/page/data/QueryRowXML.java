package org.elaya.page.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.elaya.page.core.DataException;

public class QueryRowXML extends BaseQueryXML {

	@Override
	public void handleResult(ResultSet prs, MapData pdata) throws SQLException, DataException {
		ResultSetMetaData meta=prs.getMetaData();
		int numColumns=meta.getColumnCount();
		if(prs.next()){
			for(int colCnt=1;colCnt<=numColumns;colCnt++){
				System.out.println("Set :"+meta.getColumnLabel(colCnt));
				pdata.put(meta.getColumnLabel(colCnt),prs.getObject(colCnt));
			}
		} else {
			for(int colCnt=1;colCnt<=numColumns;colCnt++){
				pdata.put(meta.getColumnLabel(colCnt),null);
			}
		}
	}

}
