package cn.njyazheng.mapper.music;

import cn.njyazheng.domain.music.Music;
import cn.njyazheng.domain.music.MusicExample;
import cn.njyazheng.vo.Pagination;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface MusicMapper {
    @SelectProvider(type = MusicSqlProvider.class, method = "countByExample")
    long countByExample(MusicExample example);

    @DeleteProvider(type = MusicSqlProvider.class, method = "deleteByExample")
    int deleteByExample(MusicExample example);

    @Delete({
            "delete from music",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Select({"<script>",
            "select * from music",
            "<where><if test=\"music.name!=null and music.name!='' \">name like concat('%',#{music.name},'%')</if></where>",
            "<where><if test=\"music.singer!=null and music.singer!='' \">singer like concat('%',#{music.singer},'%')</if></where>",
            "limit #{page.start},#{page.limit}",
            "</script>"})
    List<Music> selectByCondiction(@Param("music") Music music, @Param("page") Pagination pagination);

    @Select({"<script>",
            "select count(1) from music",
            "<where><if test=\"music.name!=null and music.name!='' \">name like concat('%',#{music.name},'%')</if></where>",
            "<where><if test=\"music.singer!=null and music.singer!='' \">singer like concat('%',#{music.singer},'%')</if></where>",
            "</script>"})
    Integer selectCountByCondition(@Param("music") Music music);

    @Insert({
            "insert into music (id, name, ",
            "singer, filename, ",
            "uri)",
            "values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
            "#{singer,jdbcType=VARCHAR}, #{filename,jdbcType=VARCHAR}, ",
            "#{uri,jdbcType=VARCHAR})"
    })
    int insert(Music record);

    @InsertProvider(type = MusicSqlProvider.class, method = "insertSelective")
    int insertSelective(Music record);

    @SelectProvider(type = MusicSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "singer", property = "singer", jdbcType = JdbcType.VARCHAR),
            @Result(column = "filename", property = "filename", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uri", property = "uri", jdbcType = JdbcType.VARCHAR)
    })
    List<Music> selectByExample(MusicExample example);

    @Select({
            "select",
            "id, name, singer, filename, uri",
            "from music",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "singer", property = "singer", jdbcType = JdbcType.VARCHAR),
            @Result(column = "filename", property = "filename", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uri", property = "uri", jdbcType = JdbcType.VARCHAR)
    })
    Music selectByPrimaryKey(String id);

    @UpdateProvider(type = MusicSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Music record, @Param("example") MusicExample example);

    @UpdateProvider(type = MusicSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") Music record, @Param("example") MusicExample example);

    @UpdateProvider(type = MusicSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Music record);

    @Update({
            "update music",
            "set name = #{name,jdbcType=VARCHAR},",
            "singer = #{singer,jdbcType=VARCHAR},",
            "filename = #{filename,jdbcType=VARCHAR},",
            "uri = #{uri,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Music record);
}