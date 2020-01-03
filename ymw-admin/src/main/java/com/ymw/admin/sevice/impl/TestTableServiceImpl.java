package com.ymw.admin.sevice.impl;

import com.ymw.admin.model.TestTable;
import com.ymw.admin.dao.TestTableMapper;
import com.ymw.admin.sevice.TestTableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yan
 * @since 2019-12-04
 */
@Service
public class TestTableServiceImpl extends ServiceImpl<TestTableMapper, TestTable> implements TestTableService {

    @Autowired
    TestTableMapper testTableMapper;
    
}
