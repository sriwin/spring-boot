package com.sriwin.batch.mapper;


import com.sriwin.constants.AppConstants;
import com.sriwin.model.DBData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBRowMapper implements RowMapper {

  @Override
  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    DBData dbRowData = new DBData();
    dbRowData.setUpdatedDate(rs.getDate(AppConstants.UPDATE_DATE_NAME_COL_NAME));
    dbRowData.setBalance(rs.getLong(AppConstants.BALANCE_NAME_COL_NAME));
    dbRowData.setAccountId(rs.getLong(AppConstants.ACCOUNT_ID_COL_NAME));
    dbRowData.setActive(rs.getLong(AppConstants.ACTIVE_COL_NAME));
    return dbRowData;
  }
}
