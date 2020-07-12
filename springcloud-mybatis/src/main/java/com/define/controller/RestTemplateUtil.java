package com.define.controller;

import com.define.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里主要是介绍RestTemplate针对几种不同请求类型和参数类型的服务调用实现
 *
 * @Author: Lea
 * @Date: 2020/2/18 22:13
 */
public class RestTemplateUtil {
    @Autowired
    RestTemplate restTemplate;

    //GET请求
    //----第一种用法返回基本数据类型
    public String getForEntityUtil1() {
        //返回的ResponseEntity是Spring对HTTP请求响应的封装，其中主要存储了HTTP的几个重要元素，
        //比如HTTP请求状态码的枚举对象HttpStatus（也就是我们常说的404、500这些错误码），
        //在它的父类HttpEntity中还存储着HTTP请求的头信息对象HttpHeaders以及泛型类型的请求体对象
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "http://USER-SERVICE/user?name={1}", String.class, "Lea"
        );
        String body = responseEntity.getBody();
        return body;
    }

    //----第二种用法返回对象类型
    public UserDTO getForEntityUtil2() {
        ResponseEntity<UserDTO> responseEntity = restTemplate.getForEntity(
                "http://USER-SERVICE/user?name={1}", UserDTO.class, "Lea"
        );
        UserDTO body = responseEntity.getBody();
        return body;
    }

    //----第三种用法中url的绑定参数使用Map类型
    public UserDTO getForEntityUtil3() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Lea");
        ResponseEntity<UserDTO> responseEntity = restTemplate.getForEntity(
                "http://USER-SERVICE/user?name={name}", UserDTO.class, params
        );
        UserDTO body = responseEntity.getBody();
        return body;
    }

    //----第四种用法使用URI对象（统一资源标识符）来替代url和urlVariables参数来制定访问地址和参数绑定
    public String getForEntityUtil4() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://USER-SERVICE/user?name={name}")
                .build()
                .expand("Lea")
                .encode();
        URI uri = uriComponents.toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                uri, String.class
        );
        String body = responseEntity.getBody();
        return body;
    }

    //----第五种用法使用getForObject，可以理解为对getForEntity的进一步封装，
    //它通过HttpMessageConverterExtractor对HTTP的请求响应体body内容进行对象转换，
    //实现请求直接返回包装好的对象内容
    public String getForObjectUtil() {
        //getForObject的三种不同的重载实现跟getForEntity类似，只是返回的是请求响应体body的内容，
        //如果不需要关注请求响应除body外的其他内容时，就可以使用这个函数
        String body = restTemplate.getForObject(
                "http://USER-SERVICE/user?name={1}", String.class, "Lea"
        );
        return body;
    }

    //POST请求
    //----第一种用法，使用postForEntity函数，三种不同的重载实现跟getForEntity类似
    public String postForEntityUtil() {
        UserDTO userDTO = new UserDTO("Lea", 27);
        //这里的String类型指的是请求响应体body内容的类型定义
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "http://USER-SERVICE/user", userDTO, String.class
        );
        String body = responseEntity.getBody();
        return body;
    }

    //----第二种用法，使用postForObject函数，三种不同的重载实现跟postForEntity类似
    public String postForObjectUtil() {
        UserDTO userDTO = new UserDTO("Lea", 27);
        String body = restTemplate.postForObject(
                "http://USER-SERVICE/user", userDTO, String.class
        );
        return body;
    }

    //----第三种用法，使用postForLocation函数
    public URI postForLocation() {
        UserDTO userDTO = new UserDTO("Lea", 27);
        URI body = restTemplate.postForLocation(
                "http://USER-SERVICE/user", userDTO
        );
        return body;
    }

    //PUT请求
    public void put() {
        Long id = 10001L;
        UserDTO userDTO = new UserDTO("Lea", 27);
        //put函数为void类型，所以没有返回内容
        restTemplate.put(
                "http://USER-SERVICE/user/{1}", userDTO, id
        );
    }

    //DELETE请求
    public void delete() {
        Long id = 10001L;
        restTemplate.delete(
                "http://USER-SERVICE/user/{1}", id
        );
    }
}
