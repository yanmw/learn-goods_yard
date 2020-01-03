package com.ymw.admin.util;

import com.ymw.core.page.ColumnFilter;
import com.ymw.core.page.PageRequest;

public class ColumnUtil {

    public static String getValueByCoumn(PageRequest pageRequest, String filterName){
        String value = null;
        ColumnFilter columnFilter = pageRequest.getColumnFilter(filterName);
        if(columnFilter != null) {
            value = columnFilter.getValue();
        }
        return value;
    }
}
