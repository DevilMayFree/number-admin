package com.freeying.admin.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * RouteDTO
 *
 * @author fx
 */
@Schema(description = "菜单路由视图返回对象")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteDTO {

    @Schema(description = "路径")
    private String path;

    @Schema(description = "前端组件")
    private String component;

    @Schema(description = "路由名称")
    private String name;

    @Schema(description = "路由元数据信息")
    private Meta meta;

    public static class Meta {

        @Schema(description = "名称")
        private String title;

        @Schema(description = "图标")
        private String icon;

        @Schema(description = "隐藏")
        private Boolean hidden;

        @Schema(description = "总是显示")
        private Boolean alwaysShow;

        @Schema(description = "页面缓存")
        private Boolean keepAlive;

        public Meta() {
            // Meta
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Boolean getHidden() {
            return hidden;
        }

        public void setHidden(Boolean hidden) {
            this.hidden = hidden;
        }

        public Boolean getAlwaysShow() {
            return alwaysShow;
        }

        public void setAlwaysShow(Boolean alwaysShow) {
            this.alwaysShow = alwaysShow;
        }

        public Boolean getKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(Boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("title", title)
                    .append("icon", icon)
                    .append("hidden", hidden)
                    .append("alwaysShow", alwaysShow)
                    .append("keepAlive", keepAlive)
                    .toString();
        }
    }

    private List<RouteDTO> children;

    public RouteDTO() {
        // RouteDTO
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<RouteDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RouteDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("path", path)
                .append("component", component)
                .append("name", name)
                .append("meta", meta)
                .append("children", children)
                .toString();
    }
}
