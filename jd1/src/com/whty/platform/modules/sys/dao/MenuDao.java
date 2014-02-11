/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.sys.entity.Menu;
import com.whty.platform.modules.sys.entity.Role;
import com.whty.platform.modules.sys.entity.User;

/**
 * 菜单DAO接口
 * @author 舒海洋
 * @version 2013-05-15
 */
public interface MenuDao extends MenuDaoCustom, CrudRepository<Menu, Long> {

	@Modifying
	@Query("update Menu set delFlag='" + Menu.DEL_FLAG_DELETE + "' where id = ?1 or parentIds like ?2")
	public int deleteById(Long id, String likeParentIds);
	
	public List<Menu> findByParentIdsLike(String parentIds);

	@Query("from Menu where delFlag='" + Menu.DEL_FLAG_NORMAL + "' order by sort")
	public List<Menu> findAllList();
	
	@Query("select distinct m from Menu m, Role r, User u where m in elements (r.menuList) and r in elements (u.roleList)" +
			" and m.delFlag='" + Menu.DEL_FLAG_NORMAL + "' and r.delFlag='" + Role.DEL_FLAG_NORMAL + 
			"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1" + // or (m.user.id=?1  and m.delFlag='" + Menu.DEL_FLAG_NORMAL + "')" + 
			" order by m.sort")
	public List<Menu> findByUserId(Long userId);
}

/**
 * DAO自定义接口
 * @author 舒海洋
 */
interface MenuDaoCustom extends BaseDao<Menu> {

}

/**
 * DAO自定义接口实现
 * @author 舒海洋
 */
@Component
class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDaoCustom {

}
