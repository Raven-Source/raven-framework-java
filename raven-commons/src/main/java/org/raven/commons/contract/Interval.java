package org.raven.commons.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interval<T extends Comparable<?>> {

    protected T start;

    protected T end;
}
