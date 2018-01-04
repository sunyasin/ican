package org.ideacreation.can.common.model.json;

import java.util.List;

/**
 *
 */
public class PageableResponse<T> {

    public Integer pageIndex;
    public Integer pageSize;
    public List<T> result;
}
