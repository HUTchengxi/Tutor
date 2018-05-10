package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.UserWebsocket;

@Mapper
public interface UserWebsocketMapper {

    @Select("select * from user_websocket where (writer=#{writer} and reader=#{reader}) || (writer=#{reader} and reader=#{writer})" +
            " order by ptime asc")
    List<UserWebsocket> loadMyWebSocketList(@Param("writer") String username, @Param("reader") String reader);

    @Insert("insert into user_websocket(writer, reader, info, ptime) values(#{writer}, #{reader}, #{info}, #{ptime})")
    Integer saveWebsocket(@Param("writer") String writer, @Param("reader") String reader, @Param("info") String info,
                          @Param("ptime") String ptime);
}