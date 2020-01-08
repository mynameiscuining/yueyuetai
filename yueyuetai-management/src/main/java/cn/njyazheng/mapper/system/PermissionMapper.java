package cn.njyazheng.mapper.system;

import cn.njyazheng.domain.system.Permission;
import cn.njyazheng.domain.system.PermissionExample;
import cn.njyazheng.vo.PermissionVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface PermissionMapper {
    @SelectProvider(type = PermissionSqlProvider.class, method = "getGradingPermissonByUsernameSql")
    List<PermissionVo> getGradingPermissonByUsername(@Param("username") String username, @Param("type") Integer type);

    @SelectProvider(type = PermissionSqlProvider.class, method = "countByExample")
    long countByExample(PermissionExample example);

    @DeleteProvider(type = PermissionSqlProvider.class, method = "deleteByExample")
    int deleteByExample(PermissionExample example);

    @Delete({
            "delete from permission",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Delete({
            "delete from role_permission",
            "where permission_id=#{id}"
    })
    int deleteRelations(Integer id);

    @Insert({
            "insert into permission (id, name, ",
            "url, parentid, sort, ",
            "type, available, ",
            "icon)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{url,jdbcType=VARCHAR}, #{parentid,jdbcType=INTEGER}, #{sort,jdbcType=INTEGER}, ",
            "#{type,jdbcType=INTEGER}, #{available,jdbcType=INTEGER}, ",
            "#{icon,jdbcType=VARCHAR})"
    })
    int insert(Permission record);

    @InsertProvider(type = PermissionSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertSelective(Permission record);

    @SelectProvider(type = PermissionSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parentid", property = "parentid", jdbcType = JdbcType.INTEGER),
            @Result(column = "sort", property = "sort", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "available", property = "available", jdbcType = JdbcType.INTEGER),
            @Result(column = "icon", property = "icon", jdbcType = JdbcType.VARCHAR)
    })
    List<Permission> selectByExample(PermissionExample example);

    @Select({
            "select",
            "id, name, url, parentid, sort, type, available, icon,backcolor",
            "from permission",
            "where id = #{id,jdbcType=INTEGER}"
    })
    Permission selectByPrimaryKey(Integer id);

    @UpdateProvider(type = PermissionSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    @UpdateProvider(type = PermissionSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    @UpdateProvider(type = PermissionSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Permission record);

    @Update({
            "update permission",
            "set name = #{name,jdbcType=VARCHAR},",
            "url = #{url,jdbcType=VARCHAR},",
            "parentid = #{parentid,jdbcType=INTEGER},",
            "sort = #{sort,jdbcType=INTEGER},",
            "type = #{type,jdbcType=INTEGER},",
            "available = #{available,jdbcType=INTEGER},",
            "icon = #{icon,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Permission record);
}