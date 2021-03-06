package com.msx7.josn.tvconnection.mima.common;

import android.util.Log;

import com.msx7.josn.tvconnection.mima.MinaConstants;
import com.msx7.josn.tvconnection.pack.message.Message;
import com.msx7.josn.tvconnection.pack.message.MessageHead;
import com.msx7.josn.tvconnection.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvconnection.pack.message.impl.MessageImpl;
import com.msx7.josn.tvconnection.pack.message.impl.body.PackBody;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class TvProtocalDecoder extends ProtocolDecoderAdapter {
    private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
    private final Charset charset;
    private int maxPackLength = MinaConstants.MAX_MESSAFE_LENGTH;

    public TvProtocalDecoder() {
        this(Charset.defaultCharset());
    }

    public TvProtocalDecoder(Charset charset) {
        this.charset = charset;
    }

    public int getMaxLineLength() {
        return maxPackLength;
    }

    public void setMaxLineLength(int maxLineLength) {
        if (maxLineLength <= 0) {
            throw new IllegalArgumentException("maxLineLength: "
                    + maxLineLength);
        }
        this.maxPackLength = maxLineLength;
    }

    private Context getContext(IoSession session) {
        Context ctx;
        ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx == null) {
            ctx = new Context();
            session.setAttribute(CONTEXT, ctx);
        }
        return ctx;
    }

    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        Log.d("way", "接受到了新的包");
        final int packHeadLength = MessageHead.HEAD_LENGTH;
        // 先获取上次的处理上下文，其中可能有未处理完的数据
        Context ctx = getContext(session);
        // 先把当前buffer中的数据追加到Context的buffer当中
        ctx.append(in);

        // 把position指向0位置，把limit指向原来的position位置
        IoBuffer buf = ctx.getBuffer();
        buf.flip();
        // 当前剩余长度大于等于包头的长度
        while (buf.remaining() >= packHeadLength) {
            buf.mark();
            if (buf.get() == MessageHead.messageStartFlag) {
                buf.reset();
                buf.mark();
                byte[] bytes = new byte[MessageHead.HEAD_LENGTH];
                buf.get(bytes, 0, MessageHead.HEAD_LENGTH);
                MessageHead head = new MessageHeadImpl(bytes);
                int packLength = head.getLength();
                if (packHeadLength - MessageHead.HEAD_LENGTH > buf.remaining()) {
                    buf.reset();
                    break;
                } else {
                    bytes = new byte[packLength - MessageHead.HEAD_LENGTH];
                    int remain = buf.remaining();
                    Log.d("way", "length " + bytes.length + "  , remain " + remain);
                    if (bytes.length > remain) {
                        Log.d("waybbbb", "length " + bytes.length + "  , remain " + remain);
                        buf.reset();
                        break;
                    }
                    buf.get(bytes, 0, bytes.length);
                    PackBody packBody = new PackBody(bytes);
                    Message message = new MessageImpl(head, packBody);
                    out.write(message);

                    Log.d("way", "解析到了新的包");
                }
            } else {
                continue;
            }
        }
        //断包处理，将剩余数据放入CONTEXT中
        if (buf.hasRemaining()) {
            IoBuffer temp = IoBuffer.allocate(maxPackLength).setAutoExpand(true);
            temp.put(buf);
            temp.flip();
            buf.clear();
            buf.put(temp);
        } else {
            buf.clear();
        }
    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
    }

    public void dispose(IoSession session) throws Exception {
        Context ctx = (Context) session.getAttribute(CONTEXT);
        if (ctx != null) {
            session.removeAttribute(CONTEXT);
        }
    }

    // 记录上下文，因为数据触发没有规模，很可能只收到数据包的一半
    // 所以，需要上下文拼起来才能完整的处理
    private class Context {
        private final CharsetDecoder decoder;
        private IoBuffer buf;
        private int matchCount = 0;
        private int overflowPosition = 0;

        private Context() {
            decoder = charset.newDecoder();
            buf = IoBuffer.allocate(MinaConstants.MAX_MESSAFE_LENGTH)
                    .setAutoExpand(true);
            // buf = IoBuffer.allocate(80).setAutoExpand(true);
        }

        public CharsetDecoder getDecoder() {
            return decoder;
        }

        public IoBuffer getBuffer() {
            return buf;
        }

        public int getOverflowPosition() {
            return overflowPosition;
        }

        public int getMatchCount() {
            return matchCount;
        }

        public void setMatchCount(int matchCount) {
            this.matchCount = matchCount;
        }

        public void reset() {
            overflowPosition = 0;
            matchCount = 0;
            decoder.reset();
        }

        public void append(IoBuffer in) {
            getBuffer().put(in);
        }
    }
//    int buflen = buf.array().length;
//    // 把position指向0位置，把limit指向原来的position位置
//
////        for (int i = 0; i < buflen; i++) {
////
////            if (i == buflen - 1 && MinaConstants.MESSASGE_START_FLAG != buf.get(i)) {
////                buf.clear();
////                throw new Exception("消息头错误");
////            } else if (i < buf.limit() && MinaConstants.MESSASGE_START_FLAG == buf.get(i)) {
////                buf.position(i);
////                break;
////            } else {
////                break;
////            }
////        }
//    if(MinaConstants.MESSASGE_START_FLAG != buf.get(0)){
//        Log.d("way", "bytes:" + Arrays.toString(buf.array()));
//        buf.clear();
//        throw new Exception("消息头错误");
//    }
//    buf.flip();
////        if (MinaConstants.MESSASGE_START_FLAG != buf.get(0)) {
////            Log.d("way", Arrays.toString(buf.array()));
////            Log.d("way", "----" + MinaConstants.MESSASGE_START_FLAG);
////
////        }
//
//
//    // byte[] originalBytes = buf.array();
//    int messageLength;// = originalBytes.length;
//    byte[] bufBytes = buf.array();
//    // 按数据包的协议进行读取
//    while (buf.remaining() >= packHeadLength) {
//        // 读取消息头部分
//        //byte[] messageBytes = null;
//        // messageBytes = Arrays.copyOfRange(originalBytes, 4,
//        // messageLength);
//        int position = buf.position();
//        MessageHead head = null;
//        if (bufBytes.length > packHeadLength) {
//            byte[] bytes = new byte[bufBytes.length - position];
//            System.arraycopy(bufBytes, position, bytes, 0, bufBytes.length - position);
//            head = new MessageHeadImpl(bytes);
//
////				head = new MessageHeadImpl(Arrays.copyOfRange(bufBytes, position,bufBytes.length));
//        }
//        buf.mark();
//        // 得到下一条消息的码流
//        //messageBytes = Arrays.copyOfRange(bufBytes, position,
//        //bufBytes.length);
//        // messageBytes = originalBytes;
//
//        // 消息的长度为消息头部长度加上消息体的长度
//        Log.d("way", "head:" + (head == null));
//
//        int getMessageLength = head.getLength();
//        Log.d("way", "getMessageLength:   " + getMessageLength);
//        //检查消息是否进行了分包，消息头消息长度大于定义的消息最大长度，则是分包的消息
//        messageLength = getMessageLength > MinaConstants.MAX_MESSAFE_LENGTH ? MinaConstants.MAX_MESSAFE_LENGTH
//                : getMessageLength;
//        Log.d("way", "messageLength:   " + messageLength);
//        Log.d("way", "packHeadLength:   " + packHeadLength);
//        Log.d("way", "buf.remaining():   " + buf.remaining());
//        Log.d("way", "maxPackLength:   " + maxPackLength);
//        Log.d("way", "boolean:   " + (messageLength >= packHeadLength
//                && messageLength - packHeadLength <= buf.remaining()));
//        // 检查读取的包头是否正常，不正常的话清空buffer
//        if (messageLength < 0 || messageLength > maxPackLength) {
//            buf.clear();
//            Log.d("way", "break:   ");
//
//            break;
//        }
//        // 读取正常的消息包，并写入输出流中，以便IoHandler进行处理
//        else if (messageLength >= packHeadLength
//                && messageLength - packHeadLength <= buf.remaining()) {
//            // MessageType.bodyLengthMap.get(head.getMessageInfo())
//            int bodyBeginIndex = position + packHeadLength;
//                /*if (!head.isLastPackHead()) {
//                    bodyEndIndex = bodyBeginIndex + maxPackLength;
//				} else {
//					// 得到最后一个包的limit，消息头的长度加
//					bodyEndIndex = bodyBeginIndex
//							+ (MessageHeadImpl.HEAD_LENGTH + MessageType.bodyLengthMap
//									.get(head.getMessageInfo()))
//							% MinaConstants.MAX_MESSAFE_LENGTH;
//				}*/
//            buf.position(bodyBeginIndex);
//            int bodyEndIndex = bodyBeginIndex + head.getLength() - MessageHead.HEAD_LENGTH;
//            byte[] packBodyBytes = new byte[bodyEndIndex - bodyBeginIndex];
//            System.arraycopy(bufBytes, bodyBeginIndex, packBodyBytes, 0, bodyEndIndex - bodyBeginIndex);
//            PackBody packBody = new PackBody(packBodyBytes);
////				PackBody packBody = new PackBody(Arrays.copyOfRange(bufBytes, bodyBeginIndex, bodyEndIndex));
//            buf.position(bodyEndIndex);
//            Message message = new MessageImpl(head, packBody);
//            out.write(message);
//        } else {
//            // 如果消息包不完整
//            // 将指针重新移动消息头的起始位置
//            buf.reset();
//            break;
//        }
//    }
//    if (buf.hasRemaining()) { // 将数据移到buffer的最前面
//        IoBuffer temp = IoBuffer.allocate(maxPackLength)
//                .setAutoExpand(true);
//        temp.put(buf);
//        int startIndex = 0;
//        //获取下一条消息的开始标识位置
//        if (temp.get() != MinaConstants.MESSASGE_START_FLAG) {
//            startIndex = temp.indexOf(MinaConstants.MESSASGE_START_FLAG);
//        }
//        if (startIndex != -1) {
//            temp.position(startIndex);
//            temp.limit(temp.remaining());
//            //temp.flip();
//            buf.clear();
//            buf.put(temp);
//        } else {
//            buf.clear();
//        }
//    } else {
//        // 如果数据已经处理完毕，进行清空
//        buf.clear();
//    }
}

