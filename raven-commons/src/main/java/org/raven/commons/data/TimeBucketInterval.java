package org.raven.commons.data;

import org.raven.commons.contract.Interval;
import org.raven.commons.enums.DownSampling;
import org.raven.commons.util.TimeBucket;
import lombok.Getter;


@Getter
public class TimeBucketInterval extends Interval<Long> {

    public DownSampling downSampling;

    public TimeBucketInterval(DateInterval dateInterval, DownSampling downSampling) {

        this.start = TimeBucket.getTimeBucket(dateInterval.getStart().getTime(), downSampling);
        this.end = TimeBucket.getTimeBucket(dateInterval.getEnd().getTime(), downSampling);

        this.downSampling = downSampling;
    }
}
