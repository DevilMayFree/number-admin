package com.freeying.framework.data.utils;

import java.util.List;

/**
 * @param <T> dtoType
 * @author fx
 */
public record PageResult<T>(List<T> content, long totalElements) {

}
