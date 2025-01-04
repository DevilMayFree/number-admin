package com.freeying.admin.number.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.freeying.framework.data.core.BasePO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

@TableName(value = "apple_gift")
public class AppleGift extends BasePO {

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 领取用户id
     */
    private Long takeUserId;

    /**
     * 领取时间
     */
    private LocalDateTime takeTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTakeUserId() {
        return takeUserId;
    }

    public void setTakeUserId(Long takeUserId) {
        this.takeUserId = takeUserId;
    }

    public LocalDateTime getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(LocalDateTime takeTime) {
        this.takeTime = takeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("content", content)
                .append("status", status)
                .append("takeUserId", takeUserId)
                .append("takeTime", takeTime)
                .toString();
    }
}
