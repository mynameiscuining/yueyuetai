package cn.njyazheng.mapper.system;

import cn.njyazheng.domain.system.Permission;
import cn.njyazheng.domain.system.PermissionExample;
import cn.njyazheng.domain.system.PermissionExample.Criteria;
import cn.njyazheng.domain.system.PermissionExample.Criterion;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class PermissionSqlProvider {

    public String countByExample(PermissionExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("permission");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(PermissionExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("permission");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String getGradingPermissonByUsernameSql(String username, Integer type) {
        SQL sql = new SQL();
        sql.SELECT_DISTINCT(" p.id,p.name,p.url,p.parentid,p.sort,p.type,p.available,p.icon,p.backcolor ").FROM(" permission p ")
                .LEFT_OUTER_JOIN(" role_permission rp on rp.permission_id=p.id ")
                .LEFT_OUTER_JOIN(" role r on r.id=rp.role_id ")
                .LEFT_OUTER_JOIN(" user_role ur on ur.role_id=r.id ")
                .LEFT_OUTER_JOIN(" user u on u.id=ur.user_id ");
        StringBuilder wheresql = new StringBuilder(" p.available=0  and r.available=0");
        if (!StringUtils.isEmpty(username)) {
            wheresql.append(" and u.username=#{username} ");
        }
        if (type != null) {
            wheresql.append(" and p.type=#{type} ");
        }
        sql.WHERE(wheresql.toString());
        sql.ORDER_BY(" p.sort");

        return sql.toString();

    }

    public String insertSelective(Permission record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("permission");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }

        if (record.getParentid() != null) {
            sql.VALUES("parentid", "#{parentid,jdbcType=INTEGER}");
        }

        if (record.getSort() != null) {
            sql.VALUES("sort", "#{sort,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getAvailable() != null) {
            sql.VALUES("available", "#{available,jdbcType=INTEGER}");
        }

        if (record.getIcon() != null) {
            sql.VALUES("icon", "#{icon,jdbcType=VARCHAR}");
        }
        if (record.getBackcolor() != null) {
            sql.VALUES("backcolor", "#{backcolor,jdbcType=VARCHAR}");
        }
        return sql.toString();
    }

    public String selectByExample(PermissionExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("name");
        sql.SELECT("url");
        sql.SELECT("parentid");
        sql.SELECT("sort");
        sql.SELECT("type");
        sql.SELECT("available");
        sql.SELECT("icon");
        sql.FROM("permission");
        applyWhere(sql, example, false);

        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }

        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Permission record = (Permission) parameter.get("record");
        PermissionExample example = (PermissionExample) parameter.get("example");

        SQL sql = new SQL();
        sql.UPDATE("permission");

        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            sql.SET("name = #{record.name,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.SET("url = #{record.url,jdbcType=VARCHAR}");
        }

        if (record.getParentid() != null) {
            sql.SET("parentid = #{record.parentid,jdbcType=INTEGER}");
        }

        if (record.getSort() != null) {
            sql.SET("sort = #{record.sort,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            sql.SET("type = #{record.type,jdbcType=INTEGER}");
        }

        if (record.getAvailable() != null) {
            sql.SET("available = #{record.available,jdbcType=INTEGER}");
        }

        if (record.getIcon() != null) {
            sql.SET("icon = #{record.icon,jdbcType=VARCHAR}");
        }

        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("permission");

        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("name = #{record.name,jdbcType=VARCHAR}");
        sql.SET("url = #{record.url,jdbcType=VARCHAR}");
        sql.SET("parentid = #{record.parentid,jdbcType=INTEGER}");
        sql.SET("sort = #{record.sort,jdbcType=INTEGER}");
        sql.SET("type = #{record.type,jdbcType=INTEGER}");
        sql.SET("available = #{record.available,jdbcType=INTEGER}");
        sql.SET("icon = #{record.icon,jdbcType=VARCHAR}");

        PermissionExample example = (PermissionExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Permission record) {
        SQL sql = new SQL();
        sql.UPDATE("permission");

        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.SET("url = #{url,jdbcType=VARCHAR}");
        }

        if (record.getParentid() != null) {
            sql.SET("parentid = #{parentid,jdbcType=INTEGER}");
        }

        if (record.getSort() != null) {
            sql.SET("sort = #{sort,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }

        if (record.getAvailable() != null) {
            sql.SET("available = #{available,jdbcType=INTEGER}");
        }

        if (record.getIcon() != null) {
            sql.SET("icon = #{icon,jdbcType=VARCHAR}");
        }
        if (record.getBackcolor() != null) {
            sql.SET("backcolor = #{backcolor,jdbcType=VARCHAR}");
        }
        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    protected void applyWhere(SQL sql, PermissionExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }

        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }

        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }

        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}