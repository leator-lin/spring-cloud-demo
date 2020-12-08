package com.define.util;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UploadRestTemplateUtil {
 
	/**
	 * 获取上传文件的restTemplate
	 * @return
	 */
	public static RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
 
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		stringHttpMessageConverter.setWriteAcceptCharset(true);
 
		List<MediaType> mediaTypeList = new ArrayList<>();
		mediaTypeList.add(MediaType.ALL);
 
		for (int i = 0; i < messageConverters.size(); i++) {
			HttpMessageConverter<?> converter = messageConverters.get(i);
			if (converter instanceof StringHttpMessageConverter) {
				messageConverters.remove(i);
				messageConverters.add(i, stringHttpMessageConverter);
			}
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				try {
					((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (converter instanceof FormHttpMessageConverter) {
				// 针对文件上传文件名乱码情况使用自定义的converter
				MyFormHttpMessageConverter myConverter = new MyFormHttpMessageConverter();
				myConverter.setCharset(StandardCharsets.UTF_8);
 
				messageConverters.remove(i);
				messageConverters.add(i, myConverter);
			}
		}
		return restTemplate;
	}
}