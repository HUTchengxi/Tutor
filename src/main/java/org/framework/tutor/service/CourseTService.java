package org.framework.tutor.service;

import org.framework.tutor.domain.CourseTreply;

/**
 * 课程家教老师评价回复服务层接口
 * @author chengxi
 */
public interface CourseTService {

    /**
     * 获取指定课程对应评价的老师回复
     * @param cid
     * @param cmid
     * @return
     */
    CourseTreply getCourseTreply(Integer cid, Integer cmid);
}
