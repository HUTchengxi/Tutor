package org.framework.tutor.service;

import org.framework.tutor.domain.TutorBtns;

import java.util.List;

/**
 * 指定家教常用链接服务层接口
 * @author chengxi
 */
public interface TutorBtnsService {

    /**
     * 获取指定家教的所有常用链接数据
     * @param username
     * @return
     */
    List<TutorBtns> getBtnsList(String username);
}
