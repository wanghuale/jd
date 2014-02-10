/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.dangpu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.entity.ServiceRecord;
import com.whty.platform.modules.dangpu.entity.Ticket;

/**
 * 当票管理DAO接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
public interface TicketDao extends TicketDaoCustom, CrudRepository<Ticket, Long> {

	@Modifying
	@Query("update Ticket set delFlag='" + ServiceRecord.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Consumer where username = ?1 and delFlag='" + ServiceRecord.DEL_FLAG_NORMAL + "' ")
	public Consumer findByUsername(String username);

	@Query("from Consumer where   delFlag='" + ServiceRecord.DEL_FLAG_NORMAL + "'  order by name ")
	public List<Consumer> findALL();

}

/**
 * DAO自定义接口
 * 
 * @author qimin
 */
interface TicketDaoCustom extends BaseDao<Ticket> {

}

/**
 * DAO自定义接口实现
 * 
 * @author qimin
 */
@Component
class TicketDaoImpl extends BaseDaoImpl<Ticket> implements TicketDaoCustom {

}
