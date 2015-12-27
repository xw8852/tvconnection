package com.msx7.josn.tvconnection.pack;


import com.msx7.josn.tvconnection.pack.message.MessageHandler;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaowei on 2015/12/8.
 */
public final class MessageHandlerLib {
    private static final MessageHandlerLib INSTANCE = new MessageHandlerLib();

    /**
     * 消息处理器集合.
     */
    private Map<String, MessageHandler> handlers = null;
    private MessageHandler handler;

    private MessageHandlerLib() {
        handlers = new HashMap<String, MessageHandler>();
        addHandlers();
    }

    public static MessageHandlerLib getInstance() {
        return INSTANCE;
    }

    /**
     * 添加所有消息处理器.
     */
    private void addHandlers() {
        //pad所需要的handler
    }

    /**
     * 添加单个消息处理器
     *
     * @param messageInfo the message info
     * @param decoder     the handler
     */
    public void addHandler(String messageInfo, MessageHandler decoder) {
        handlers.put(messageInfo, decoder);
//        this.handler = decoder;
    }

    public void removeHandler(String messageInfo) {
        handlers.remove(messageInfo);
    }

    /**
     * 根据消息信息获取指定消息处理器.
     *
     * @param msgInfo 消息信息
     * @return 消息处理器
     */
    public MessageHandler getHandler(String msgInfo) {
        return handlers.get(msgInfo);
    }
}
