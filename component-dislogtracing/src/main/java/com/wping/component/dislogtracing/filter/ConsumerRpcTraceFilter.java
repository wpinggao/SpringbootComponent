package com.wping.component.dislogtracing.filter;

import com.wping.component.base.constatns.CommonConstants;
import com.wping.component.dislogtracing.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

@Activate(group = {org.apache.dubbo.common.constants.CommonConstants.CONSUMER})
public class ConsumerRpcTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(CommonConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = CommonUtils.genTraceId();
        }

        RpcContext.getContext().setAttachment(CommonConstants.TRACE_ID, traceId);
        return invoker.invoke(invocation);
    }
}
