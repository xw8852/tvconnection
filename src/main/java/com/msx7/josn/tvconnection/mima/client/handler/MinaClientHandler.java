package com.msx7.josn.tvconnection.mima.client.handler;

import android.util.Log;

import com.msx7.josn.tvconnection.mima.common.util.MinaUtil;
import com.msx7.josn.tvconnection.pack.message.Message;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class MinaClientHandler implements IoHandler {
    private static MinaClientHandler minaClientHandler = null;

    public static MinaClientHandler getInstances() {
        if (null == minaClientHandler) {
            minaClientHandler = new MinaClientHandler();
        }
        return minaClientHandler;
    }

    private MinaClientHandler() {

    }

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        Log.d("way", "Created---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "Created---RemoteAddress:" + ioSession.getRemoteAddress().toString());
        Log.d("way", "Created---ServiceAddress:" + ioSession.getServiceAddress().toString());
        Log.d("way", "服务器与客户端会话创建了");
        handMsg("服务器与客户端会话创建了\r\n" + ioSession.getLocalAddress().toString() + "\r\n" + ioSession.getServiceAddress().toString());
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与客户端会话打开了");
        handMsg("服务器与客户端会话打开了\r\n" + ioSession.getLocalAddress().toString() + "\r\n" + ioSession.getServiceAddress().toString());
        if (Statushandler != null)
            Statushandler.handStatus(IStatusHandler.STATUC_CONNECT, ioSession);
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        Log.d("way", "Closed---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "Closed---ServiceAddress:" + ioSession.getServiceAddress().toString());
        Log.d("way", "服务器与客户端会话关闭了");
        handMsg("服务器与客户端会话关闭了");
        if (Statushandler != null)
            Statushandler.handStatus(IStatusHandler.STATUC_FINISH, ioSession);
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        Log.d("way", "服务器与的客户端会话空闲了");
        handMsg("服务器与的客户端会话空闲了");
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Log.d("way", "服务器与的客户端会话异常了");
        handMsg("服务器与的客户端会话异常了");
        if (Statushandler != null)
            Statushandler.handStatus(IStatusHandler.STATUC_ERROR, ioSession);
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话接受了");
        handMsg("服务器与的客户端会话接受了");
        MinaUtil.messageRecevie((Message) o, ioSession);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话发送了");
        handMsg("服务器与的客户端会话发送了");
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与的客户端会话inputClosed了");
        ioSession.close(true);
        handMsg("服务器与的客户端会话inputClosed了");
    }

    public void handMsg(String str) {
        if (handler != null) {
            handler.handLog(str);
        }
    }

    ILogHandler handler;
    IStatusHandler Statushandler;

    public void setLogHandler(ILogHandler handler) {
        this.handler = handler;
    }

    public void setStatusHandler(IStatusHandler handler) {
        this.Statushandler = handler;
    }

    public interface ILogHandler {
        void handLog(String string);
    }

    public interface IStatusHandler {
        public static final int STATUC_CONNECT = 0X001;
        public static final int STATUC_FINISH = 0X002;
        public static final int STATUC_ERROR = 0X003;

        void handStatus(int status, IoSession ioSession);
    }
}
