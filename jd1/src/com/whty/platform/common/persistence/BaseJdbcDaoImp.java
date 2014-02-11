package com.whty.platform.common.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbcDaoImp implements BaseJdbcDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public final JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public List<Map<String, Object>> query(String sql, List<Object> param) {
		return getJdbcTemplate().queryForList(sql, param.toArray());
	}

	public List<Map<String, Object>> query(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}

}