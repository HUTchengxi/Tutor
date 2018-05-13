package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseTreply;
import org.framework.tutor.mapper.CourseTreplyMapper;
import org.framework.tutor.service.CourseTreplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 课程家教老师评价回复服务层实现类
 * @author chengxi
 */
@Component
public class CourseTreplyServiceImpl implements CourseTreplyService {

    @Autowired
    private CourseTreplyMapper courseTreplyMapper;

    /**
     * 获取指定课程的对应用户评价的老师回复
     * @param cid
     * @param cmid
     * @return
     */
    @Override
    public CourseTreply getCourseTreply(Integer cid, Integer cmid) {

        return courseTreplyMapper.getCourseTreply(cid, cmid);
    }
}
