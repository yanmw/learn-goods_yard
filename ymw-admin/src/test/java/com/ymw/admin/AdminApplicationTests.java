package com.ymw.admin;

import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.model.sys.SysDept;
import com.ymw.admin.model.sys.SysUser;
import com.ymw.admin.model.TestTable;
import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.admin.sevice.sys.SysDeptService;
import com.ymw.admin.sevice.TestTableService;
import com.ymw.admin.util.ApplicationValueUtils;
import com.ymw.admin.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {
    @Autowired
    TestTableService testTableService;

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    ApplicationValueUtils applicationValueUtils;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SysBasisService sysBasisService;

    @Test
    public void testInsert() {
        TestTable table = new TestTable();
        table.setName("mpTest");
        table.setSexMan("test11");
        boolean b = testTableService.insert(table);
        if (b) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }

    @Test
    public void testFindChild() {
        List<SysDept> list = sysDeptService.findAll();
        List<SysDept> tempList = new ArrayList<>();
        Long id = 36L;
        sysDeptService.orgRecursion(tempList, list, id);
        System.out.println(tempList.size());
    }

    @Test
    public void testDeleteDept() {
        List<SysDept> list = new ArrayList<>();
        SysDept sysDept = new SysDept();
        sysDept.setId(33L);
        list.add(sysDept);
        int status = sysDeptService.delete(list);
        System.out.println("状态是：" + status);
    }

    @Test
    public void testApplicationValue() {
        String img = applicationValueUtils.getImg();
        System.out.println(img);
    }

    @Test
    public void testRedis() {
        redisUtil.set("redisTemplate", "测试数据", SysConstants.DATABASE0);
        String str = redisUtil.get("redisTemplate",SysConstants.DATABASE0).toString();
        System.out.println(str+"-------------");
    }

    @Test
    public void testResdisList() {
        List<SysDept> list = sysDeptService.findAll();
        redisUtil.set("test",list,SysConstants.DATABASE0);
        List<SysDept> str = (List<SysDept>) redisUtil.get("test",SysConstants.DATABASE0);
        System.out.println(str);
        System.out.println(str.size());
    }

    @Test
    public void testBasis() {
        sysBasisService.setBasisInfo();
        Object object = redisUtil.get(SysConstants.ALL_USER,SysConstants.DATABASE0);
        List<SysUser> list = (List<SysUser>)object;
        System.out.println(list);
    }
}
