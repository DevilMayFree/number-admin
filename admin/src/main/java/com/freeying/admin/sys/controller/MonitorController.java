package com.freeying.admin.sys.controller;

import com.freeying.common.core.web.Result;
import com.freeying.admin.sys.domain.bo.Server;
import com.freeying.admin.sys.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author fx
 */
@RestController
@Tag(description = "sysMonitor", name = "系统：服务监控管理")
@RequestMapping("/api/sys/monitor")
public class MonitorController {

    private final MonitorService serverService;

    public MonitorController(MonitorService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    @Operation(summary = "查询服务监控")
    public Result<Map<String, Object>> queryMonitor() {
        return Result.success(serverService.getServers());
    }

    @GetMapping(value = "/server")
    public Result<Server> server() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.success(server);
    }
}
