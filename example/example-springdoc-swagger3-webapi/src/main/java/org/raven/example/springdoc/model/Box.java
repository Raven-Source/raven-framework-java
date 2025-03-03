package org.raven.example.springdoc.model;

import org.raven.example.springdoc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author by yanfeng
 * date 2021/9/16 15:03
 */
@Data
@FieldNameConstants
@Schema(title = "盒子")
public class Box {

    @NotNull
    private Long[] ids;
    private String name;

    @Schema(title = "是否打开")
    @NotNull
    private Boolean isOpen;

    @NotNull
    private BigInteger price;

    @Nullable
    private BigDecimal totalPrice;

    private BigDecimal[] discounts;

    private BigInteger[] codes;

    private Status status;

}
