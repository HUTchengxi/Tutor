package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.CommonImgsrcApi;
import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonImgsrcApiImpl implements CommonImgsrcApi {

    @Autowired
    private CommonImgsrcService commonImgsrcService;


    //TODO：后续可以考虑加入redis缓存
    @Override
    public String getAll() throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<CommonImgsrc> commonImgsrcList = commonImgsrcService.getAll();

        for (CommonImgsrc commonImgsrc: commonImgsrcList) {
            Map<String, Object> rowMap = new HashMap<>(4);
            rowMap.put("imgsrc", commonImgsrc.getImgsrc());
            rowMap.put("title", commonImgsrc.getTitle());
            rowList.add(rowMap);
        }
        resultMap.put("list", rowList);

        return gson.toJson(resultMap);
    }
}
