package com.sriwin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DBData {
  private Date updatedDate;
  private Long accountId;
  private Long balance;
  private Long active;
}