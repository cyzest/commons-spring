package io.github.cyzest.commons.spring.dao;

import io.github.cyzest.commons.spring.model.EnumCode;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatis EnumCode Handler
 */
public abstract class EnumCodeHandler<T extends Enum<T> & EnumCode> implements TypeHandler<T> {

    private final Class<T> targetClass;

    public EnumCodeHandler(Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException();
        }
        this.targetClass = targetClass;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return getEnumTypeByResult(result);
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return getEnumTypeByResult(result);
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return getEnumTypeByResult(result);
    }

    private T getEnumTypeByResult(String result) {
        T enumCode = null;
        if (result != null && !result.isEmpty()) {
            T[] constants = targetClass.getEnumConstants();
            for (T constant : constants) {
                if (constant.getCode().equals(result)) {
                    enumCode = constant;
                    break;
                }
            }
        }
        return enumCode;
    }

}
