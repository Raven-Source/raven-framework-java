package org.raven.example.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.Entity;

import java.util.Map;

@Getter
@Setter
public abstract class BaseDto<TKey, TA, TB> implements Entity<TKey> {

    @Schema(title = "id")
    private TKey id;

    private Map<TA, TB> userInfo;
}
