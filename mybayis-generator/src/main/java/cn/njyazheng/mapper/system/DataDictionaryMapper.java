package cn.njyazheng.mapper.system;

import cn.njyazheng.domain.DataDictionary;
import cn.njyazheng.domain.DataDictionaryExample;
import cn.njyazheng.domain.DataDictionaryKey;
import java.util.List;
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

public interface DataDictionaryMapper {
    @SelectProvider(type=DataDictionarySqlProvider.class, method="countByExample")
    long countByExample(DataDictionaryExample example);

    @DeleteProvider(type=DataDictionarySqlProvider.class, method="deleteByExample")
    int deleteByExample(DataDictionaryExample example);

    @Delete({
        "delete from data_dictionary",
        "where serial_number = #{serialNumber,jdbcType=INTEGER}",
          "and paramno = #{paramno,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(DataDictionaryKey key);

    @Insert({
        "insert into data_dictionary (serial_number, paramno, ",
        "paramname, name)",
        "values (#{serialNumber,jdbcType=INTEGER}, #{paramno,jdbcType=INTEGER}, ",
        "#{paramname,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR})"
    })
    int insert(DataDictionary record);

    @InsertProvider(type=DataDictionarySqlProvider.class, method="insertSelective")
    int insertSelective(DataDictionary record);

    @SelectProvider(type=DataDictionarySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="serial_number", property="serialNumber", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="paramno", property="paramno", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="paramname", property="paramname", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<DataDictionary> selectByExample(DataDictionaryExample example);

    @Select({
        "select",
        "serial_number, paramno, paramname, name",
        "from data_dictionary",
        "where serial_number = #{serialNumber,jdbcType=INTEGER}",
          "and paramno = #{paramno,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="serial_number", property="serialNumber", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="paramno", property="paramno", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="paramname", property="paramname", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    DataDictionary selectByPrimaryKey(DataDictionaryKey key);

    @UpdateProvider(type=DataDictionarySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") DataDictionary record, @Param("example") DataDictionaryExample example);

    @UpdateProvider(type=DataDictionarySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") DataDictionary record, @Param("example") DataDictionaryExample example);

    @UpdateProvider(type=DataDictionarySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DataDictionary record);

    @Update({
        "update data_dictionary",
        "set paramname = #{paramname,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR}",
        "where serial_number = #{serialNumber,jdbcType=INTEGER}",
          "and paramno = #{paramno,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DataDictionary record);
}