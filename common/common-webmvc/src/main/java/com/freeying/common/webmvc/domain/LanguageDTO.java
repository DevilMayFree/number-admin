package com.freeying.common.webmvc.domain;

import com.freeying.common.core.entity.DTO;
import com.freeying.common.webmvc.utils.I18nUtil;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * LanguageDTO
 *
 * @author fx
 */
public class LanguageDTO extends DTO {

    @Schema(description = "当前国际化环境", hidden = true)
    private String language = I18nUtil.getLanguage();

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
