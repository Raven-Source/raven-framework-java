//package org.raven.commons.data;
//
//import jdk.nashorn.internal.ir.annotations.Ignore;
//
//import java.util.List;
//
//public interface Pageable {
//
//    int getPage();
//
//    void setPage(int pago);
//
//    int getSize();
//
//    void setSize(int size);
//
//    List<Sort.Order> getOrders();
//
//    @Ignore
//    default void setSort(Sort sort) {
//        setOrders(sort.getOrders());
//    }
//
//    void setOrders(List<Sort.Order> orders);
//}
