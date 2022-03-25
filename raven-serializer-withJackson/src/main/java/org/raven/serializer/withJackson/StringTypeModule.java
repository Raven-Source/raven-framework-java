package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.raven.commons.data.StringType;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:26
 */
public class StringTypeModule extends SimpleModule {

    protected SerializerSetting setting;

    public StringTypeModule(SerializerSetting setting) {
        super("StringTypeModule", PackageVersion.VERSION);

        this.setting = setting;
        this.addSerializer(StringType.class, StringTypeSerializer.INSTANCE);
    }

    @Override
    public void setupModule(SetupContext context) {

        super.setupModule(context);
        context.addDeserializers(new StringTypeDeserializers());
    }

}
