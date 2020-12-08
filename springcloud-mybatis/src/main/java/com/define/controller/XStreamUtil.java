package com.define.controller;

import com.define.dto.CarDTO;
import com.define.dto.RecordDTO;
import com.define.dto.ResponseDTO;
import com.define.dto.UserDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class XStreamUtil {

//    public static void main(String[] args) {
//        bean2Xml();
//    }

    public static void bean2Xml() {
        UserDTO userDTO = new UserDTO("小明", 27);
        RecordDTO recordDTO = new RecordDTO("记录", "10yuan");
        CarDTO carDTO = new CarDTO(userDTO, recordDTO);
        ResponseDTO<CarDTO> responseDTO = new ResponseDTO<CarDTO>();
        responseDTO.setData(carDTO);
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        //对指定的类使用Annotations进行序列化，这步非常关键，同时开启注解
        xStream.processAnnotations(CarDTO.class);
        //xStream对象设置默认安全防护，同时设置允许的类，否则会报错：xStream:Security framework of XStream not initialized, XStream is probably vulnerable.
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{CarDTO.class});
        String xmlStr = xStream.toXML(responseDTO);
        System.out.println(xmlStr);
    }

    @Test
    public static void xml2Bean() {

    }
}
