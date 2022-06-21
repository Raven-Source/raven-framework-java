package org.raven.commons.data.mybatis.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.raven.commons.data.mybatis.config.Configuration;
import org.raven.commons.data.mybatis.type.ObjectMapperWrapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@MappedTypes({Map.class, List.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.CHAR})
public class JsonTypeHandler<E> extends BaseTypeHandler<E> {

    private Class<E> type;
    private ObjectMapperWrapper objectMapperWrapper;

    public JsonTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.objectMapperWrapper = Configuration.INSTANCE.getObjectMapperWrapper();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, objectMapperWrapper.toString(parameter));

    }

    @Override
    public E getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);
        return valueOf(value);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String value = resultSet.getString(columnIndex);
        return valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return valueOf(value);
    }

    private E valueOf(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        return objectMapperWrapper.fromString(value, type);
    }
}
