/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.modules.kamen.entity.Goods;

/**
 * 对账Entity
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
@Entity
@Table(name = "tab_goods_consumer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsConsumer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 消费者编号
	 */
	private Consumer consumer;

	/**
	 * 消费者编号
	 */
	private Goods goods;

	/**
	 * 销售价
	 */
	private Double sell_price;

	private double spreadprice; // 进售差价

	public GoodsConsumer() {
		super();
	}

	public GoodsConsumer(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Double getSell_price() {
		return sell_price;
	}

	public void setSell_price(Double sell_price) {
		this.sell_price = sell_price;
	}

	public double getSpreadprice() {
		return spreadprice;
	}

	public void setSpreadprice(double spreadprice) {
		this.spreadprice = spreadprice;
	}

}
