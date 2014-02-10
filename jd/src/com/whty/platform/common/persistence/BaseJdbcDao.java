/**
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.common.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseJdbcDao {
	public JdbcTemplate getJdbcTemplate();

	public List<Map<String, Object>> query(String sql, List<Object> param);
}