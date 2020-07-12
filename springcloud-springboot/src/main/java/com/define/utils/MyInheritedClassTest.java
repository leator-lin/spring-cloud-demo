package com.define.utils;

import com.define.domain.IInheritedInterface;
import com.define.domain.IInheritedInterfaceChild;
import com.define.domain.MyInheritedClass;
import com.define.domain.MyInheritedClassUseInterface;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class MyInheritedClassTest {

    @Test
    public void testInherited() {
        {
            Annotation[] annotations = MyInheritedClass.class.getAnnotations();
            System.out.println("--------------------------1--------------------------");
            Arrays.stream(annotations).forEach(System.out::print);
//            Assert.isTrue(Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)), "检查正确！！！");
//            Assert.isTrue(Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)), "检查正确！！！");
        }
        {
            Annotation[] annotations = MyInheritedClassUseInterface.class.getAnnotations();
            System.out.println("--------------------------2--------------------------");
            Arrays.stream(annotations).forEach(System.out::print);
//            Assert.isTrue(Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)), "检查正确！！！");
//            Assert.isTrue(Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)), "检查正确！！！");
        }
        {
            Annotation[] annotations = IInheritedInterface.class.getAnnotations();
            System.out.println("--------------------------3--------------------------");
            Arrays.stream(annotations).forEach(System.out::print);
//            Assert.isTrue(Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)), "检查正确！！！");
//            Assert.isTrue(Arrays.stream(annotations).anyMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)), "检查正确！！！");
        }
        {
            Annotation[] annotations = IInheritedInterfaceChild.class.getAnnotations();
            System.out.println("--------------------------4--------------------------");
            Arrays.stream(annotations).forEach(System.out::print);
//            Assert.isTrue(Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(IsInheritedAnnotation.class)), "检查正确！！！");
//            Assert.isTrue(Arrays.stream(annotations).noneMatch(l -> l.annotationType().equals(NoInherritedAnnotation.class)), "检查正确！！！");
        }
    }
}