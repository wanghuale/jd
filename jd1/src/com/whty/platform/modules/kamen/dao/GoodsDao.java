/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.kamen.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import com.whty.platform.common.base.dao.BaseDao;
import com.whty.platform.common.base.dao.BaseDaoImpl;
import com.whty.platform.modules.kamen.entity.Goods;

/**
 * 商品DAO接口
 * @author 舒海洋
 * @version 2013-11-22
 */
public interface GoodsDao extends GoodsDaoCustom, CrudRepository<Goods, Long> {

	@Modifying
	@Query("update Goods set delFlag='" + Goods.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author 舒海洋
 */
interface GoodsDaoCustom extends BaseDao<Goods> {

}

/**
 * DAO自定义接口实现
 * @author 舒海洋
 */
@Component
class GoodsDaoImpl extends BaseDaoImpl<Goods> implements GoodsDaoCustom {

}
