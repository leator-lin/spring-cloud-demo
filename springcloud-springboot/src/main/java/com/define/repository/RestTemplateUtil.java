package com.define.repository;

import com.define.dto.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * RestTemplate的应用
 *
 * @Author: Lea
 * @Date: 2019/8/12 20:23
 */
public class RestTemplateUtil {

    @Autowired
    RestTemplate restTemplate;

    // 不带参数的get请求
    public void restTemplateGet() {
        Notice notice = restTemplate.getForObject("http://xxx.top/notice/list/1/5", Notice.class);
        System.out.println(notice);
    }

    // 带参数的get请求，使用占位符传参
    public void restTemplateGet2() {
        Notice notice = restTemplate.getForObject("http://xxx.top/notice/list/{1}/{2}", Notice.class,1, 5);
        System.out.println(notice);
    }

    // 带参数的get请求，使用map装载参数
    public void restTemplateGet3() {
        Map<String, String> map = new HashMap();
        map.put("start", "1");
        map.put("page", "5");
        Notice notice = restTemplate.getForObject("http://xxx.top/notice/list/{1}/{2}", Notice.class,map);
        System.out.println(notice);
}

    public void restTemplateGetEntity() {
        ResponseEntity<Notice> entity = restTemplate.getForEntity("http://fantj.top/notice/list/1/5", Notice.class);

        HttpStatus statusCode = entity.getStatusCode();
        System.out.println("statusCode.is2xxSuccessful()" + statusCode.is2xxSuccessful());

        Notice body = entity.getBody();
        System.out.println("entity.getBody()" + body);


    }
}
