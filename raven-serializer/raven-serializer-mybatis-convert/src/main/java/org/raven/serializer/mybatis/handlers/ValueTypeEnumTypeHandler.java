package org.raven.serializer.mybatis.handlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.data.ValueType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
@Slf4j
@MappedTypes({ValueType.class})
@MappedJdbcTypes({JdbcType.INTEGER, JdbcType.BIGINT, JdbcType.FLOAT, JdbcType.DOUBLE, JdbcType.DECIMAL})
public class ValueTypeEnumTypeHandler<E extends ValueType<?>> extends BaseTypeHandler<E> {

    private Class<E> type;

    public ValueTypeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {

        Class<?> clazz = SerializableTypeUtils.getGenericType(type);

        Number value;

        if (clazz.equals(Integer.class)) {
            value = rs.getInt(columnName);
        } else if (clazz.equals(Long.class)) {
            value = rs.getLong(columnName);
        } else if (clazz.equals(BigInteger.class)) {
            value = rs.getLong(columnName);
        } else if (clazz.equals(Double.class)) {
            value = rs.getDouble(columnName);
        } else if (clazz.equals(Float.class)) {
            value = rs.getFloat(columnName);
        } else if (clazz.equals(BigDecimal.class)) {
            value = rs.getBigDecimal(columnName);
        } else {
            value = rs.getLong(columnName);
        }

        return valueOf(value);
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        Class<?> clazz = SerializableTypeUtils.getGenericType(type);

        Number value;

        if (clazz.equals(Integer.class)) {
            value = rs.getInt(columnIndex);
        } else if (clazz.equals(Long.class)) {
            value = rs.getLong(columnIndex);
        } else if (clazz.equals(BigInteger.class)) {
            value = rs.getLong(columnIndex);
        } else if (clazz.equals(Double.class)) {
            value = rs.getDouble(columnIndex);
        } else if (clazz.equals(Float.class)) {
            value = rs.getFloat(columnIndex);
        } else if (clazz.equals(BigDecimal.class)) {
            value = rs.getBigDecimal(columnIndex);
        } else {
            value = rs.getLong(columnIndex);
        }

        return valueOf(value);
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        Class<?> clazz = SerializableTypeUtils.getGenericType(type);

        Number value;

        if (clazz.equals(Integer.class)) {
            value = cs.getInt(columnIndex);
        } else if (clazz.equals(Long.class)) {
            value = cs.getLong(columnIndex);
        } else if (clazz.equals(BigInteger.class)) {
            value = cs.getLong(columnIndex);
        } else if (clazz.equals(Double.class)) {
            value = cs.getDouble(columnIndex);
        } else if (clazz.equals(Float.class)) {
            value = cs.getFloat(columnIndex);
        } else if (clazz.equals(BigDecimal.class)) {
            value = cs.getBigDecimal(columnIndex);
        } else {
            value = cs.getLong(columnIndex);
        }

        return valueOf(value);
    }

    /**
     * 数值和枚举类型转换
     */
    private E valueOf(Number value) {
        return SerializableTypeUtils.valueOf(type, value);
    }


}
