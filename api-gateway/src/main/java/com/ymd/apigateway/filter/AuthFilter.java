//package com.ymd.apigateway.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import com.ymd.apigateway.constant.RedisConstant;
//import com.ymd.apigateway.utils.CookieUtil;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//
///**
// * 权限拦截（区分买家和卖家）
// */
//@Component
//public class AuthFilter extends ZuulFilter {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//        /**
//         * /order/create 只能买家访问（cookie里有openid）
//         * /order/finish 只能卖家访问（cookie里有token，并且对应redis有值）
//         * /product/list 都可访问
//         */
//        if (StringUtils.equals(request.getRequestURI(),"/order/order/create")){
//            Cookie cookie = CookieUtil.get(request, "openid");
//            if (cookie == null || StringUtils.isBlank(cookie.getValue())){
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        if (StringUtils.equals(request.getRequestURI(),"/order/order/finish")){
//            Cookie cookie = CookieUtil.get(request, "token");
//            if (cookie == null
//                    ||
//                    StringUtils.isBlank(cookie.getValue())
//                    ||
//                    this.stringRedisTemplate.opsForValue().get(
//                    String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())).isEmpty()){
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//        return null;
//    }
//}
