package com.define.utils;

import com.define.domain.ExportUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * easyPOI的excel导入导出接口
 *
 * @author linyincheng
 * @date 2020/4/15 16:21
 */
@RestController
@RequestMapping("/util")
public class EasyPoiExcelUtil {

    @RequestMapping("/import/users")
    @ResponseBody
    public List<ExportUser> importUsers(@RequestParam MultipartFile file) {
        return EasyPoiUtils.importExcel(file, ExportUser.class);
    }

    @RequestMapping(value = "/export/users", method = RequestMethod.GET)
    public void exportUsers(HttpServletResponse response) {
        List<ExportUser> userList = getUserList();
        EasyPoiUtils.exportExcel(userList, "用户列表", "用户报表", ExportUser.class, "用户明细报表.xls", response);
    }

    private List<ExportUser> getUserList() {
        List<ExportUser> userList = new ArrayList<>();
        userList.add(new ExportUser("tom", new Date()));
        userList.add(new ExportUser("jack", new Date()));
        userList.add(new ExportUser("123", new Date()));
        return userList;
    }
}
