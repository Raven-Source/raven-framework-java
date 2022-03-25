package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.raven.commons.data.StringType;
import org.raven.commons.data.ValueType;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:26
 */
public class SerializableTypeModule extends SimpleModule {

    protected SerializerSetting setting;

    public SerializableTypeModule(SerializerSetting setting) {
        super("SerializableTypeModule", PackageVersion.VERSION);

        this.setting = setting;
        this.addSerializer(ValueType.class, ValueTypeSerializer.INSTANCE);
        this.addSerializer(StringType.class, StringTypeSerializer.INSTANCE);
    }

    @Override
    public void setupModule(SetupContext context) {

        super.setupModule(context);
        context.addDeserializers(new SerializableTypeDeserializers());
    }

}
