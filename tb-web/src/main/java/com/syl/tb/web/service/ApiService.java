package com.syl.tb.web.service;

import com.syl.tb.common.bean.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiService implements BeanFactoryAware {
    @Autowired
    private RequestConfig requestConfig;

    private BeanFactory beanFactory;

    public String doGet(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        try {
            response = getHttpClient().execute(get);
            if (response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        }finally {
            if (response!=null){
                response.close();
            }
        }
        return null;
    }


    public String doGet(String url, Map<String,String> params) throws IOException, URISyntaxException {
        URIBuilder uri = new URIBuilder(url);
        for (Map.Entry<String,String> entry:params.entrySet()){
            uri.setParameter(entry.getKey(),entry.getValue());
        }
        return doGet(uri.build().toString());
    }
    public HttpResult doPost(String url) throws IOException {
        return doPost(url,null);
    }


    public HttpResult doPost(String url,Map<String,String> params) throws IOException {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (null == params){
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String,String> entry : params.entrySet()){
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(list);
            httpPost.setEntity(form);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private CloseableHttpClient getHttpClient(){
        return beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
