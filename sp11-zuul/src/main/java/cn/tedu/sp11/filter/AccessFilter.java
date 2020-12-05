package cn.tedu.sp11.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import cn.tedu.web.util.JsonResult;

@Component
public class AccessFilter  extends ZuulFilter{
	/**
	 * 对当前请求进行判断,判断这次请求,是否要执行代码过滤
	 * 
	 * 如果访问item-service,执行过滤,判断是否登录
	 * 访问其他服务,跳过过滤代码,继续正常执行流程
	 */
	@Override
	public boolean shouldFilter() {
		//判断正在请求的服务id
			RequestContext ctx = RequestContext.getCurrentContext();
			String serviceId=(String) ctx.get(FilterConstants.SERVICE_ID_KEY);
				if ("item-service".equals(serviceId)) {
					return true;
				}
		
		return false;
	}
	/**
	 * 过滤方法,判断用户是否登录,如果没有登录应该阻止他继续访问
	 */
	@Override
	public Object run() throws ZuulException {
		//
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String token = request.getParameter("token");
		if (StringUtils.isEmpty(token)) {
			//没有token,表示没有登录
			//继续阻止访问
			ctx.setSendZuulResponse(false);
			
			//发回未登录的响应
			ctx.setResponseBody(JsonResult.ok("not login").code(JsonResult.NOT_LOGIN).toString());
			ctx.setResponseStatusCode(JsonResult.NOT_LOGIN);
		}
		
		
		return null;//zuul当前版本,没有启用这个返回值
	}

	@Override
	public String filterType() {

		
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 6;//在第5个过滤器,在Context上下文对象中,添加了service id
	}

}
