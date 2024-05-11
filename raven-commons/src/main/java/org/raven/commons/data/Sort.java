package org.raven.commons.data;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.raven.commons.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanfeng
 * date 2023/1/31 14:17
 */
@Data
public class Sort {

    private static final Sort UNSORTED = Sort.by(new Order[0]);
    public static final Direction DEFAULT_DIRECTION = Direction.ASC;
    private final List<Order> orders;

    protected Sort(List<Order> orders) {
        this.orders = orders;
    }

    private Sort(Direction direction, List<String> properties) {
        if (properties != null && !properties.isEmpty()) {
            this.orders = properties.stream().map((it) -> new Order(direction, it)).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
    }

    /**
     * Returns a new {@link Sort} with the current setup but descending order direction.
     *
     * @return
     */
    public Sort descending() {
        return withDirection(Direction.DESC);
    }

    /**
     * Returns a new {@link Sort} with the current setup but ascending order direction.
     *
     * @return
     */
    public Sort ascending() {
        return withDirection(Direction.ASC);
    }

    public static Sort by(@NonNull String... properties) {
        return properties.length == 0 ? unsorted() : new Sort(DEFAULT_DIRECTION, Arrays.asList(properties));
    }

    public static Sort by(@NonNull List<Order> orders) {
        return orders.isEmpty() ? unsorted() : new Sort(orders);
    }

    public static Sort by(@NonNull Order... orders) {
        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(@NonNull Direction direction, @NonNull String... properties) {
        if (properties.length == 0) {
            throw new IllegalArgumentException("At least one property must be given!");
        }
        return by(Arrays.stream(properties).map((it) -> {
            return new Order(direction, it);
        }).collect(Collectors.toList()));
    }

    public static Sort unsorted() {
        return UNSORTED;
    }

    private Sort withDirection(Direction direction) {

        return Sort.by(orders.stream().map(it -> new Order(direction, it.getProperty())).collect(Collectors.toList()));
    }


    @Getter
    public static class Order {

        /**
         * property
         */
        private final String property;

        /**
         * ASC,DESC;
         */
        private final Direction direction;

        public Order(Direction direction, String property) {
            if (StringUtils.isBlank(property)) {
                throw new IllegalArgumentException("Property must not null or empty!");
            }

            this.direction = direction == null ? Sort.DEFAULT_DIRECTION : direction;
            this.property = property;
        }
    }

    /**
     *
     */
    public enum Direction implements StringType {

        ASC("ASC"),
        DESC("DESC");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static Direction of(String value) {

            if (value != null) {
                for (Direction item : values()) {
                    if (item.getValue().equals(value)) {
                        return item;
                    }
                }

            }
            return null;
        }
    }
}
