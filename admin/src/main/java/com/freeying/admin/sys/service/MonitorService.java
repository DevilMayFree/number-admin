package com.freeying.admin.sys.service;

import java.util.Map;

/**
 * @author fx
 */
public interface MonitorService {

    /**
     * 查询数据分页
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getServers();
}
