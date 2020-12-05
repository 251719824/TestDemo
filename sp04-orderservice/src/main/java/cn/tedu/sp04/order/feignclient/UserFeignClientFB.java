package cn.tedu.sp04.order.feignclient;

import org.springframework.stereotype.Component;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;

@Component
public class UserFeignClientFB implements UserFeignClient {

	@Override
	public JsonResult<User> getUser(Integer userId) {
		if (Math.random()<04) {
			return JsonResult.ok(new User(userId,"缓存name:"+userId,"缓存pwd:"+userId));
		}
		return JsonResult.err("无法获取用户信息");
	}

	@Override
	public JsonResult addScore(Integer userId, Integer score) {
		return JsonResult.err("无法增加用户积分");
	}

}
