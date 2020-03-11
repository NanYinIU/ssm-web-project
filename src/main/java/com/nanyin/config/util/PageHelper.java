package com.nanyin.config.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PageHelper {

    private static final String DESC = "desc";
    private static final String ASC = "asc";
    /**
     * 传入 offset,limit,sort,direct
     * @param offset
     * @param limit
     * @return
     */
    public static PageRequest generatePageRequest(Integer offset, Integer limit, String direct,@NotNull List<String> sort){
        offset = initOffset(offset);
        limit = initLimit(limit);
        Sort initSort = initSort(direct,sort);
        return PageRequest.of(offset-1,limit,initSort);
    }

    public static PageRequest generatePageRequest(Integer offset, Integer limit, String direct,@NotNull String sort){
        offset = initOffset(offset);
        limit = initLimit(limit);
        Sort initSort = initSort(direct,sort);
        return PageRequest.of(offset-1,limit,initSort);
    }

    public static PageRequest generatePageRequest(Integer offset, Integer limit, @NotNull Sort sort){
        offset = initOffset(offset);
        limit = initLimit(limit);
        return PageRequest.of(offset-1,limit,sort);
    }

    private static Sort initSort(String direct,String sort) {
        return Sort.by(initDirect(direct),sort);
    }

    private static Sort initSort(String direct,List<String> sort) {
        return Sort.by(initDirect(direct),(String[]) sort.toArray(new String[sort.size()]));
    }

    private static Sort.Direction initDirect(String direct) {
        if(DESC.equals(direct)){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    private static Integer initLimit(Integer limit) {
        if(limit == null){
            return Integer.MAX_VALUE;
        }
        return limit;
    }

    private static Integer initOffset(Integer offset) {
        if(offset == null){
            return 1;
        }
        return offset;
    }

    public static PageRequest generatePageRequest(Integer offset, Integer limit){
        return generatePageRequest(offset,limit,null,"id");
    }

    public static PageRequest generatePageRequest(Integer offset, Integer limit,String sort){
        return generatePageRequest(offset,limit,null,sort);
    }

    public static PageRequest generatePageRequest(Integer offset, Integer limit,List<String> sort){
        return generatePageRequest(offset,limit,null,sort);
    }
}
