package cn.njyazheng.mapper.system;

import cn.njyazheng.domain.system.Role;
import cn.njyazheng.domain.system.RoleExample;
import cn.njyazheng.vo.Pagination;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RoleMapper {
    @SelectProvider(type = RoleSqlProvider.class, method = "countByExample")
    long countByExample(RoleExample example);

    @DeleteProvider(type = RoleSqlProvider.class, method = "deleteByExample")
    int deleteByExample(RoleExample example);

    @Delete({
            "delete from role",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Delete({
            "delete from role_permission",
            "where role_id = #{id,jdbcType=INTEGER}"
    })
    int deleteRolePermisionByRoleId(Integer id);

    @Delete({
            "delete from role_permission where role_id=#{roleId} and permission_id=#{permissionId}"
    })
    int deleteRolePermission(@Param("roleId") Integer roleid, @Param("permissionId") Integer permissionId);

    @Insert({"insert into role_permission(role_id,permission_id) value(#{roleId},#{permissionId})"})
    int insertRolePermisson(@Param("roleId") Integer roleid, @Param("permissionId") Integer permissionId);

    @Insert({
            "insert into role (id, name, ",
            "available)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{available,jdbcType=INTEGER})"
    })
    int insert(Role record);

    @InsertProvider(type = RoleSqlProvider.class, method = "insertSelective")
    int insertSelective(Role record);

    @SelectProvider(type = RoleSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "available", property = "available", jdbcType = JdbcType.INTEGER)
    })
    List<Role> selectByExample(RoleExample example);

    @Select({
            "select",
            "id, name, available,`desc`",
            "from role",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "available", property = "available", jdbcType = JdbcType.INTEGER)
    })
    Role selectByPrimaryKey(Integer id);

    @Select({
            "select permission_id from role_permission",
            "where role_id=#{id}"
    })
    List<Integer> selectCheck(Integer id);


    @Select({" select r.id ,r.name,r.available from role r ",
            " left join user_role ur on r.id=ur.role_id ",
            " left join user u on u.id=ur.user_id ",
            " where  u.username=#{username} "})
    List<Role> selectRoleByUserName(String username);

    @Select({"<script>",
            " select count(1) from role ",
            "<where><if test=\"role.name!=null and role.name!=''\"> name like CONCAT('%',#{role.name},'%') </if></where>",
            "<where><if test=\"role.available!=null\">available=#{role.available}</if></where>",
            "</script>"
    })
    int selectCoountByConditions(@Param("role") Role role);

    @Select({"<script>",
            " select id,name,available,`desc` from role ",
            "<where><if test=\" role.name!=null and role.name!='' \"> name like concat('%',#{role.name},'%') </if></where>",
            "<where><if test=\" role.available!=null \">available=#{role.available}</if></where>",
            " limit #{page.start},#{page.limit} ",
            "</script>"
    })
    List<Role> selectByConditions(@Param("role") Role role, @Param("page") Pagination pagination);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Role record);

    @Update({
            "update role",
            "set name = #{name,jdbcType=VARCHAR},",
            "available = #{available,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);
}