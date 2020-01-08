package cn.njyazheng.mapper.system;

import cn.njyazheng.domain.system.UserInfo;
import cn.njyazheng.domain.system.UserInfoExample;

import java.util.List;

import cn.njyazheng.vo.Pagination;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserInfoMapper {
    @SelectProvider(type = UserInfoSqlProvider.class, method = "countByExample")
    long countByExample(UserInfoExample example);

    @DeleteProvider(type = UserInfoSqlProvider.class, method = "deleteByExample")
    int deleteByExample(UserInfoExample example);

    @Delete({
            "delete from user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into user (id, phone, ",
            "username, password, ",
            "locked, status)",
            "values (#{id,jdbcType=INTEGER}, #{phone,jdbcType=VARCHAR}, ",
            "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
            "#{locked,jdbcType=INTEGER}, #{status,jdbcType=INTEGER})"
    })
    int insert(UserInfo record);

    @InsertProvider(type = UserInfoSqlProvider.class, method = "insertSelective")
    int insertSelective(UserInfo record);

    @SelectProvider(type = UserInfoSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "locked", property = "locked", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<UserInfo> selectByExample(UserInfoExample example);

    @Select(" select role_id from user_role where user_id=#{id} ")
    List<Integer> selectRoleID(Integer id);

    @Insert({
            "insert into user_role(user_id,role_id) value(#{user_id},#{role_id});"
    })
    int insertUserRole(@Param("user_id") Integer userID, @Param("role_id") Integer roleId);

    @Delete(" delete from user_role where user_id=#{user_id} and role_id=#{role_id} ;")
    int deleteUserRole(@Param("user_id") Integer userID, @Param("role_id") Integer roleId);

    @Select({
            "select",
            "id, phone, username, password, locked, status",
            "from user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "locked", property = "locked", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    UserInfo selectByPrimaryKey(Integer id);


    @Select({"<script>",
            " select id, phone, username, password, locked, status from user",
            " where 1=1",
            "<if test='userInfo.status!=null'>and status=#{userInfo.status}</if>  ",
            "<if test='userInfo.locked!=null'>and locked=#{userInfo.locked}</if> ",
            " <if test=\"userInfo.username!=null and userInfo.username!='' \">",
            " and username like concat('%',#{userInfo.username},'%') </if>",
            " limit #{pagination.start},#{pagination.limit} ",
            "</script>"})
    List<UserInfo> selectForPaganation(@Param("userInfo") UserInfo userInfo, @Param("pagination") Pagination pagination);

    @UpdateProvider(type = UserInfoSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    @UpdateProvider(type = UserInfoSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    @UpdateProvider(type = UserInfoSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserInfo record);

    @Update({
            "update user",
            "set phone = #{phone,jdbcType=VARCHAR},",
            "username = #{username,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "locked = #{locked,jdbcType=INTEGER},",
            "status = #{status,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserInfo record);
}