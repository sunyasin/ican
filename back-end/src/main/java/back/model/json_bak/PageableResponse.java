package back.model.json_bak;

import java.util.List;

/**
 *
 */
public class PageableResponse<T> {

    public Integer pageIndex;
    public Integer pageSize;
    public List<T> result;
}
