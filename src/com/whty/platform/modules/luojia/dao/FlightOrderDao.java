package com.whty.platform.modules.luojia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.luojia.entity.FlightOrder;

public interface FlightOrderDao extends FlightOrderDaoCustom, CrudRepository<FlightOrder, Long> {
}
	
/**
 * DAO自定义接口
 * 
 * @author 舒海洋
 */
interface FlightOrderDaoCustom extends BaseDao<FlightOrder> {

}

/**
 * DAO自定义接口实现
 * 
 * @author 舒海洋
 */
@Component
class FlightOrderDaoImpl extends BaseDaoImpl<FlightOrder> implements FlightOrderDaoCustom {

}
