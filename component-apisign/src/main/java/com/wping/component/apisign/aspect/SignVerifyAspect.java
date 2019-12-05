package com.wping.component.apisign.aspect;

import com.wping.component.apisign.annotation.SignVerify;
import com.wping.component.apisign.dto.AppInfo;
import com.wping.component.apisign.exception.VerifyFailedException;
import com.wping.component.apisign.properties.ApiSignProperties;
import com.wping.component.apisign.request.ApiBaseReqVo;
import com.wping.component.apisign.service.ApiSignService;
import com.wping.component.apisign.utils.MD5SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.Map;

/**
 * describe:签名验证切面
 *
 * @author Wping.gao
 * @date 2018/12/05
 */
@Aspect
public class SignVerifyAspect implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SignVerifyAspect.class);

    private ApiSignProperties apiSignProperties;
    private ApiSignService apiSignService;

    public SignVerifyAspect(ApiSignProperties apiSignProperties) {
        this.apiSignProperties = apiSignProperties;
    }

    @Before("@annotation(signVerify)")
    public void before(JoinPoint point, SignVerify signVerify) {

        String methodName = "方法调用前签名验证, 方法:" + point.getSignature().getName();

        if (!apiSignProperties.getCheckSignOpen()) {
            logger.info(methodName + "-退出验签, 开关关闭");
            return;
        }

        Object[] objects = point.getArgs();
        ApiBaseReqVo apiBaseReqVo = null;
        for (Object object : objects) {
            if (object instanceof ApiBaseReqVo) {
                apiBaseReqVo = (ApiBaseReqVo) object;
                break;
            }
        }

        if (apiBaseReqVo == null) {
            logger.error(methodName + "-验签失败，报文对象为空");
            throw new VerifyFailedException("报文对象为空");
        }

        // 机构验证
        if (StringUtils.isEmpty(apiBaseReqVo.getAppId())) {
            logger.error(methodName + "-验签失败，appId为空");
            throw new VerifyFailedException("appId为空");
        }

        if (StringUtils.isEmpty(apiBaseReqVo.getBizData())) {
            logger.error(methodName + "-验签失败，业务数据为空");
            throw new VerifyFailedException("业务数据为空");
        }

        if (apiBaseReqVo.getTimestamp() == null) {
            logger.error(methodName + "-验签失败，时间戳为空");
            throw new VerifyFailedException("时间戳为空");
        }

        if (StringUtils.isEmpty(apiBaseReqVo.getNonce())) {
            logger.error(methodName + "-验签失败，流水号为空");
            throw new VerifyFailedException("流水号为空");
        }

        // 时间戳有效期验证
        Long now = System.currentTimeMillis();
        if ((now - apiBaseReqVo.getTimestamp()) > apiSignProperties.getSecondsOfExpiryTime() * 1000) {
            logger.error(methodName + "-验签失败，时间戳超过有效期{}秒", apiSignProperties.getSecondsOfExpiryTime());
            throw new VerifyFailedException("时间戳超过有效期" + apiSignProperties.getSecondsOfExpiryTime() + "秒");
        }

        // 检验是否重复提交
        if (!signVerify.resubmit()) {
            if (apiSignService.checkExistOrSetNonce(apiBaseReqVo.getAppId(), apiBaseReqVo.getNonce(), apiSignProperties.getSecondsOfExpiryTime())) {
                logger.error(methodName + "-验签失败，流水号重复");
                throw new VerifyFailedException("流水号重复");
            }
        }

        methodName += ", appId:" + apiBaseReqVo.getAppId();

        AppInfo appInfo = apiSignService.loadApiSignByAppId(apiBaseReqVo.getAppId());
        if (appInfo == null) {
            logger.error(methodName + "-验签失败，机构信息为空");
            throw new VerifyFailedException("机构信息不存在");
        }

        //验证签名
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("appId", apiBaseReqVo.getAppId());
        requestMap.put("bizData", apiBaseReqVo.getBizData());
        requestMap.put("timestamp", apiBaseReqVo.getTimestamp());
        requestMap.put("nonce", apiBaseReqVo.getNonce());

        if (!MD5SignUtils.verify(requestMap, appInfo.getAppSecret(), apiBaseReqVo.getSign())) {
            logger.error(methodName + "-验签失败");
            throw new VerifyFailedException("验签失败");
        }

        logger.debug(methodName + "-验签成功");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.apiSignService = applicationReadyEvent.getApplicationContext().getBean(ApiSignService.class);
    }

}
