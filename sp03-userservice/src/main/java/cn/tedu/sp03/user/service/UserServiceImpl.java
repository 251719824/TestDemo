package cn.tedu.sp03.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RefreshScope  //配置动态刷新,刷新到的新数据可以重新注入到这个对象,
public class UserServiceImpl implements UserService{
	
	@Value("${sp.user-service.users}")
	private String userJson;
	
	
	@Override
	public User getUser(Integer id) {
		log.info("users json String :"+userJson);
		List<User> list = JsonUtil.from(userJson, new TypeReference<List<User>>() {});
		for (User u : list) {
			if (u.getId().equals(id)) {
				return u;
			}
		}
		
		return new User(id, "name"+id, "pwd"+id);
	}

	@Override
	public void addScore(Integer id, Integer score) {
		log.info("增加用户积分,userId="+id+"增加积分"+score);
		
	}

}
