package com.wping.component.http.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http client 业务逻辑处理类
 */
public class WpingHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(WpingHttpClient.class);

    private static final String UTF8 = "UTF-8";

    private CloseableHttpClient syncHttpClient;
    private RequestConfig requestConfig;
    private RequestConfig requestConfigProxyHttp;
    private RequestConfig requestConfigProxyHttps;

    public WpingHttpClient(CloseableHttpClient syncHttpClient, RequestConfig requestConfig, RequestConfig requestConfigProxyHttp, RequestConfig requestConfigProxyHttps) {
        this.syncHttpClient = syncHttpClient;
        this.requestConfig = requestConfig;
        this.requestConfigProxyHttp = requestConfigProxyHttp;
        this.requestConfigProxyHttps = requestConfigProxyHttps;
    }

    /**
     * 向指定的url发送一次post请求,参数是List<NameValuePair>
     *
     * @param baseUrl             请求地址
     * @param headerMap 设置头部
     * @param basicNameValuePairs 请求参数,格式是List<NameValuePair>
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public String httpSyncPost(String baseUrl, Map<String, String> headerMap, List<BasicNameValuePair> basicNameValuePairs, Boolean isProxy) {

        String methodName = "httpSyncPost baseUrl:" + baseUrl;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(baseUrl);
            httpPost.setConfig(getRequestConfig(baseUrl, isProxy));
            // 设置头部
            setHeader(httpPost, headerMap);

            if (null != basicNameValuePairs) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs, UTF8);
                httpPost.setEntity(entity);
            }

            response = syncHttpClient.execute(httpPost);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    /**
     * 向指定的url发送一次post请求,参数是字符串
     *
     * @param baseUrl    请求地址
     * @param headerMap  请求Header
     * @param postString 请求参数,格式是json.toString()
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestBody接收参数
     */
    public String httpSyncPost(String baseUrl, Map<String, String> headerMap, String postString, Boolean isProxy) {

        String methodName = "httpSyncPost baseUrl:" + baseUrl + ", postString:" + postString;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(baseUrl);
            httpPost.setConfig(getRequestConfig(baseUrl, isProxy));
            if (StringUtils.isEmpty(postString)) {
                logger.error(methodName + "-httpSyncPost missing post String");
                throw new Exception("httpSyncPost missing post String");
            }

            // 设置头部
            setHeader(httpPost, headerMap);

            StringEntity stringEntity = new StringEntity(postString, UTF8);
            stringEntity.setContentEncoding(UTF8);
            stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setEntity(stringEntity);

            response = syncHttpClient.execute(httpPost);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    /**
     * 向指定的url发送一次post请求,参数是字节数组
     *
     * @param baseUrl    请求地址
     * @param headerMap  请求Header
     * @param postBytes 请求参数byte数组
     * @param contentType
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestBody接收参数
     */
    public String httpSyncPost(String baseUrl, Map<String, String> headerMap, byte[] postBytes, String contentType, Boolean isProxy) {

        String methodName = "httpSyncPost baseUrl:" + baseUrl;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(baseUrl);
            httpPost.setConfig(getRequestConfig(baseUrl, isProxy));

            // 设置头部
            setHeader(httpPost, headerMap);

            httpPost.setEntity(new ByteArrayEntity(postBytes));
            if (StringUtils.isEmpty(contentType)) {
                httpPost.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
            } else {
                httpPost.setHeader("Content-type", contentType);
            }

            response = syncHttpClient.execute(httpPost);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    /**
     * 向指定的url发送一次post请求,参数是httpEntity
     *
     * @param baseUrl    请求地址
     * @param headerMap  请求Header
     * @param httpEntity 请求参数http实体
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestBody接收参数
     */
    public String httpSyncPost(String baseUrl, Map<String, String> headerMap, HttpEntity httpEntity, Boolean isProxy) {

        String methodName = "httpSyncPost baseUrl:" + baseUrl;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(baseUrl);
            httpPost.setConfig(getRequestConfig(baseUrl, isProxy));

            // 设置头部
            setHeader(httpPost, headerMap);

            httpPost.setEntity(httpEntity);

            response = syncHttpClient.execute(httpPost);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    /**
     * 向指定的url发送一次get请求,参数是List<NameValuePair>
     *
     * @param baseUrl             请求地址
     * @param headerMap 设置头部
     * @param basicNameValuePairs 请求参数,格式是List<NameValuePair>
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public String httpSyncGet(String baseUrl, Map<String, String> headerMap, List<BasicNameValuePair> basicNameValuePairs, Boolean isProxy) {

        String methodName = "httpSyncGet baseUrl:" + baseUrl;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(baseUrl);
            httpGet.setConfig(getRequestConfig(baseUrl, isProxy));
            // 设置头部
            setHeader(httpGet, headerMap);

            if (basicNameValuePairs != null) {
                String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(basicNameValuePairs));
                httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + getUrl));
            } else {
                httpGet.setURI(new URI(httpGet.getURI().toString()));
            }

            response = syncHttpClient.execute(httpGet);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    /**
     * 向指定的url发送一次get请求,参数是字符串
     *
     * @param baseUrl        请求地址
     * @param headerMap 设置头部
     * @param urlParamString 请求参数,格式是String
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public String httpSyncGet(String baseUrl, Map<String, String> headerMap, String urlParamString, Boolean isProxy) {

        String methodName = "httpSyncGet baseUrl:" + baseUrl + ", urlParamString:" + urlParamString;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(baseUrl);
            httpGet.setConfig(getRequestConfig(baseUrl, isProxy));
            // 设置头部
            setHeader(httpGet, headerMap);

            if (!StringUtils.isEmpty(urlParamString)) {
                httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + urlParamString));
            } else {
                httpGet.setURI(new URI(httpGet.getURI().toString()));
            }

            response = syncHttpClient.execute(httpGet);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }


    /**
     * 向指定的url发送一次get请求,参数是字符串
     *
     * @param baseUrl 请求地址
     * @param headerMap 设置头部
     * @param isProxy 是否代理
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public String httpSyncGet(String baseUrl, Map<String, String> headerMap, Boolean isProxy) {

        String methodName = "httpSyncGet baseUrl:" + baseUrl;
        logger.debug(methodName + "-start...");

        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(baseUrl);
            httpGet.setConfig(getRequestConfig(baseUrl, isProxy));
            // 设置头部
            setHeader(httpGet, headerMap);

            httpGet.setURI(new URI(httpGet.getURI().toString()));
            response = syncHttpClient.execute(httpGet);
            logger.info(methodName + "-response status code:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, UTF8);
                logger.debug(methodName + "-response result:" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error(methodName + "-failed!!!", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(methodName + "-close response failed!!!", e);
                }
            }
        }
        return null;
    }

    public void destroy() {
        try {
            syncHttpClient.close();
        } catch (Exception e) {
            logger.error("close httpclient failed!!!", e);
        }
    }

    // 设置头部
    private void setHeader(HttpRequestBase httpRequestBase, Map<String, String> headerMap) {

        // 设置头部
        if (httpRequestBase != null) {
            if (!CollectionUtils.isEmpty(headerMap)) {
                Iterator<Map.Entry<String, String>> iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    httpRequestBase.setHeader(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    // 获取RequestConfig统一入口
    private RequestConfig getRequestConfig(String urlValue, Boolean isProxy) {

        if (isProxy != null && isProxy) {
            try {
                URL url = new URL(urlValue);
                if ("http".equalsIgnoreCase(url.getProtocol())) {
                    return requestConfigProxyHttp;
                } else if ("https".equalsIgnoreCase(url.getProtocol())) {
                    return requestConfigProxyHttps;
                }
            } catch (Exception e) {
                logger.error("通过url动态获取RequestConfig失败", e);
            }
        }
        return requestConfig;
    }
}