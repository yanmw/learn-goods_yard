package com.ymw.admin.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.model.TestTable;
import com.ymw.admin.sevice.sys.SysDeptService;
import com.ymw.admin.sevice.TestTableService;
import com.ymw.admin.util.Log;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yan
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/testTable")
@Api(tags = "测试swagger2和mybatisPlus")
public class TestTableController {
    @Autowired
    TestTableService testTableService;

    @Autowired
    SysDeptService sysDeptService;

    @PostMapping("/insertTable")
    @ApiOperation(value = "测试插入数据")
    @Log(title = "测试新增数据")
    public HttpResult testInsert(@RequestBody TestTable table){
        if (table.getId() == null) {
            boolean b = testTableService.insert(table);
            if (b) {
                System.out.println("插入成功");
                return HttpResult.ok();
            } else {
                System.out.println("插入失败");
                return HttpResult.error("插入失败");
            }
        } else {
            boolean b = testTableService.updateById(table);
            if (b) {
                System.out.println("更新成功");
                return HttpResult.ok();
            } else {
                System.out.println("更新失败");
                return HttpResult.error("更新失败");
            }
        }

    }

    @ApiOperation(value = "测试查看多个")
    @GetMapping("testFindAll")
    public HttpResult testFindAll() {
        EntityWrapper<TestTable> ew = new EntityWrapper<>();
        Page<TestTable> page = new Page<>(2,3);
        page = testTableService.selectPage(page,ew);
        return HttpResult.ok(page);
    }

}

