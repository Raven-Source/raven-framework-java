package org.raven.commons.data;

import lombok.Data;

/**
 * @author by yanfeng
 * date 2021/9/22 17:26
 */
@Data
public class User implements Deletable {

    private Long id;
    private String name;
    private Boolean deleted;

    public static void main(String[] args) {

        System.out.println("end");
    }

}
