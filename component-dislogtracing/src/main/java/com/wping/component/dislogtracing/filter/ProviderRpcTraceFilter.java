package com.wping.component.dislogtracing.filter;

import com.wping.component.base.constatns.CommonConstants;
import com.wping.component.dislogtracing.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

@Activate(group = {org.apache.dubbo.common.constants.CommonConstants.PROVIDER})
public class ProviderRpcTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment(CommonConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = CommonUtils.genTraceId();
        }

        //设置日志traceId变量
        MDC.put(CommonConstants.TRACE_ID, traceId);

        RpcContext.getContext().setAttachment(CommonConstants.TRACE_ID, traceId);

        try {
            return invoker.invoke(invocation);
        } finally {
            MDC.remove(CommonConstants.TRACE_ID);
        }

    }


}
