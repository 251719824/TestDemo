package cn.tedu.sp04.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.order.feignclient.ItemFeignClient;
import cn.tedu.sp04.order.feignclient.UserFeignClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OderServiceImpl implements OrderService {
	
	@Autowired
	private UserFeignClient userClient;
	
	@Autowired
	private ItemFeignClient itemClient;
	
	@Override
	public Order getOrder(String orderId) {
		//TODO:调用商品服务,获取商品列表
		JsonResult<User> user = userClient.getUser(7);
		//TODO:调用用户服务,获取服务
		JsonResult<List<Item>> items = itemClient.getItems(orderId);
		
		
		
		log.info("获取订单,orderId="+orderId);
		Order order = new Order();
		order.setId(orderId);
		order.setUser(user.getData());
		order.setItems(items.getData());
		
		
		return order;
	}

	@Override
	public void addOrder(Order order) {
		/**
		 * 1.调用商品服务,减少商品库存
		 * 2.调用用户服务,增加用户积分
		 */
		JsonResult number = itemClient.decreaseNumber(order.getItems());
		userClient.addScore(7,100);
		log.info("保存订单:"+order);
	}

}
