package com.freeying.common.webmvc.handler;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局处理
 *
 * @author fx
 */
@ControllerAdvice
public class GlobalControllerHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor propertyEditor = new StringTrimmerEditor(false);
        binder.registerCustomEditor(String.class, propertyEditor);
    }

}
