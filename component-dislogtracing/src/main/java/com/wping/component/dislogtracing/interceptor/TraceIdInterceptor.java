package com.wping.component.dislogtracing.interceptor;

import com.wping.component.base.constatns.CommonConstants;
import com.wping.component.dislogtracing.utils.CommonUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TraceIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // generate traceId
        String traceId = CommonUtils.genTraceId();

        // put traceId
        MDC.put(CommonConstants.TRACE_ID, traceId);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // clear traceId
        MDC.remove(CommonConstants.TRACE_ID);
        super.afterCompletion(request, response, handler, ex);
    }

}
