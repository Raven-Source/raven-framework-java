//package org.raven.logger;
//
//import net.logstash.logback.decorate.JsonFactoryDecorator;
//import tools.jackson.core.json.JsonFactory;
//
///**
// * @author yi.liang
// * date 2019.12.02 14:18
// */
//public class LogJsonFactoryDecorator implements JsonFactoryDecorator {
//
//    @Override
//    public JsonFactory decorate(JsonFactory factory) {
//
//        factory.setCodec(JsonUtil.getMapper());
//        return factory;
//    }
//
//}