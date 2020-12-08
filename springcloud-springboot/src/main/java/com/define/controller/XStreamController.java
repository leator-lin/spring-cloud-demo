package com.define.controller;

import com.define.domain.Person;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/XstreamUtil")
public class XStreamController {

    public static void main(String[] args) {
       /* Person person = new Person();
        person.setName("Lea");
        person.setSex("男");
        person.setAge(27);
        Hand hands = new Hand(29);
        Leg leg = new Leg(70);
        person.setHands(hands);
        person.setLegs(leg);
        bean2Xml(person);*/
        Map<String, Map> map = new HashMap<>();
        Map<String, String> test = new HashMap<>();
        test.put("heihei", "lala");
        map.put("haha", test);
        System.out.println(map);
    }

    @GetMapping("/bean2Xml")
    public static void bean2Xml(Person person) {
        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(person);
        System.out.println(xml);
    }
}
