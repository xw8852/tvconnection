package scret.com.msx7.josn.tvconnection.pack.message.impl;


import scret.com.msx7.josn.tvconnection.pack.message.Message;
import scret.com.msx7.josn.tvconnection.pack.message.MessageBody;
import scret.com.msx7.josn.tvconnection.pack.message.MessageHead;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class MessageImpl implements Message {
    /**
     * 消息头
     */
    private MessageHead head = null;

    /**
     * 消息体
     */
    private MessageBody body = null;

    /**
     * @param head
     *            消息头
     * @param body
     *            消息体
     */
    public MessageImpl(MessageHead head, MessageBody body) {
        this.head = head;
        this.body = body;
    }

    /**
     * 获取消息头.
     *
     * @return 消息头
     */
    @Override
    public MessageHead getMessageHead() {
        return head;
    }

    /**
     * 获取消息体.
     *
     * @return 消息体
     */
    @Override
    public MessageBody getMessageBody() {
        return body;
    }


}
