package com.sriwin.constants;

public interface AppConstants {
  // JPA Entities & Repositories
  String ORACLE_HIBERNATE_PROPERTIES = "hibernate_oracle.properties";
  String SOURCE_TABLE_NAME = "source_table";
  String TARGET_TABLE_NAME = "target_table";
  String SOURCE_QRY = "select * from " + SOURCE_TABLE_NAME;
  String SOURCE_SELECT_CLAUSE_QRY = "select col_01, col_02, col_01";
  String SOURCE_FROM_CLAUSE_QRY = "from " + SOURCE_TABLE_NAME;

  String SEPARATOR = ",";
  String UPDATE_DATE_NAME_COL_NAME = "updated_date";
  String ACCOUNT_ID_COL_NAME = "account_id";
  String ACTIVE_COL_NAME = "active";
  String BALANCE_NAME_COL_NAME = "balance";
  String TARGET_COLS = ACCOUNT_ID_COL_NAME + SEPARATOR +
      BALANCE_NAME_COL_NAME + SEPARATOR +
      ACTIVE_COL_NAME + SEPARATOR +
      UPDATE_DATE_NAME_COL_NAME;
  String TARGET_INSERT_QRY = "INSERT INTO " +
      TARGET_TABLE_NAME + " (" + TARGET_COLS + ")" +
      "VALUES (:accountId, :balance, :active, :updatedDate)";
}