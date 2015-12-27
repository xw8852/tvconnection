package com.msx7.josn.tvconnection.action;


import com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/21.
 */
public class TouchGetBody implements MessageBody, IDecoder {
    public static final int LENGTH = 9;
    public int width;
    public int height;

    @Override
    public void decoder(byte[] bytes) {
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 0, _byte, 0, 4);
        width = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4, _byte, 0, 4);
        height = ByteUtil.bytesToInt(_byte);
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[LENGTH];
        System.arraycopy(ByteUtil.intToBytes(width), 0, _byte, 0, 4);
        System.arraycopy(ByteUtil.intToBytes(height), 0, _byte, 4, 4);
        _byte[LENGTH - 1] = messageEndFlag;
        return _byte;
    }
}
