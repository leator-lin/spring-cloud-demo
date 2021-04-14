package com.define.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.define.entity.TUser;
import com.define.mapper.MyBaseMapper;
import com.define.service.ITUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lea
 * @since 2020-12-08
 */
@RestController
@RequestMapping("/user")
public class TUserController {

    @Resource
    private ITUserService tUserService;

    @Resource
    private MyBaseMapper myBaseMapper;

    // 使用
    @GetMapping("/list")
    public List<TUser> list(@RequestParam long current, @RequestParam long size) {
        Page<TUser> page = new Page<>(current, size);
        IPage<TUser> tUserIPage = tUserService.pageList(page);
        return tUserIPage.getRecords();
    }

    @GetMapping("/saveBatch")
    public boolean saveBatch() {
        List<TUser> tUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TUser tUser = new TUser("小林" + i + "号");
            tUsers.add(tUser);
        }
        // batchSize的意思是当执行到5条时，执行sqlSession.flushStatements()进行预插入
        return tUserService.saveBatch(tUsers, 5);
    }

    // 保存或者更新，查询是否有该主键，有就更新，否就插入
    @GetMapping("/saveOrUpdate")
    public boolean saveOrUpdate() {
        TUser tUser = new TUser("Billy jack", 28, "test9@baomidou.com", LocalDateTime.now(), LocalDateTime.now());
        return tUserService.saveOrUpdate(tUser);
//        UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.setSql("name = 'Java' where id = 1");
//        return tUserService.saveOrUpdate(tUser, updateWrapper);
    }

    @GetMapping("/remove")
    public boolean remove() {
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("id", 1);
//        return tUserService.remove(queryWrapper);

        // 删除数据根据map里面的条件，比如下面的是：delete t_user where name = 'Billy jack' and age = '27'
        Map<String, Object> removeMap = new HashMap<>();
        removeMap.put("name", "Billy jack");
        removeMap.put("age", "27");
        return tUserService.removeByMap(removeMap);
    }

    @GetMapping("/update")
    public boolean update() {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setSql("name = 'MyBatis Plus'");
        return tUserService.update(updateWrapper);
    }

    @GetMapping("/get")
    public boolean get() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", "MyBatis Plus");
        Map<String, Object> tUserMap = tUserService.getMap(queryWrapper);
        return true;
    }

    // 分页查询
    @GetMapping("/page")
    public List<TUser> page() {
        IPage<TUser> page = new Page<>(1, 20);
        IPage<TUser> tUserMap = tUserService.page(page);
        List<TUser> tUsers = tUserMap.getRecords();
        return tUsers;
    }

    @GetMapping("/chain")
    public boolean chain() {
        List<TUser> tUsers = tUserService.query().eq("name", "MyBatis Plus").list();
        List<TUser> tUsers1 = tUserService.lambdaQuery().eq(TUser::getName, "MyBatis Plus").list();

        tUserService.update().eq("age", 27).remove();
        tUserService.lambdaUpdate().eq(TUser::getAge, 27).remove();
        return true;
    }

    /**
     * 自定义sql注入器
     * 相关的类：
     * com.define.config.MyBaseMapper
     * com.define.injector.MyLogicSqlInjector
     * com.define.methods.MysqlInsertAllBatch
     */
    @GetMapping("/mySqlInjector")
    public void mySqlInjector() {
        int id = 1009991;
        List<TUser> batchList = new ArrayList<>(2);
        batchList.add(new TUser().setId(id++).setName("林城").setAge(28).setEmail("111@qq.com"));
        batchList.add(new TUser().setId(id).setName("林城").setAge(28).setEmail("112@qq.com"));
        myBaseMapper.mysqlInsertAllBatch(batchList);
    }

    // 使用MyBatis Plus的乐观锁，只要在对象和数据库增加version字段，然后在version字段上加注解@Verison即可，
    // 不用再加where version = xxx条件，也不需要手动给version + 1，mybatis plus会给我们做这件事情
    @GetMapping("/updateUseVersion")
    public void updateUseVersion() {
        TUser tUser1 = tUserService.getById(15);
        TUser tUser2 = tUserService.getById(15);

        tUser1.setName("MyBatis");
        tUser2.setName("MyBatis Test");
        boolean result = tUserService.updateById(tUser1);
        boolean resultTest = tUserService.updateById(tUser2);
        System.out.println(result);
        System.out.println(resultTest);
    }

}
