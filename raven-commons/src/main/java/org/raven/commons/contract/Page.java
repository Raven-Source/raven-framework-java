package org.raven.commons.contract;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.05.23 11:52
 */
public interface Page<T> {

    /**
     * Returns the number of total pages.
     *
     * @return the number of total pages
     */
    int getTotalPages();

    /**
     * Returns the page to be returned.
     *
     * @return the page to be returned.
     */
    int getPageNumber();

    /**
     * Returns the number of items to be returned.
     *
     * @return the number of items of that page
     */
    int getPageSize();

    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    long getTotalElements();


    /**
     * Returns the page content as {@link List}.
     *
     * @return
     */
    List<T> getContent();
}
