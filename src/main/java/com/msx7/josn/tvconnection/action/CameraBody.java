package com.msx7.josn.tvconnection.action;


import com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/21.
 */
public class CameraBody implements MessageBody,IDecoder {
    public static final int LENGTH = 8+ MSG_END_FLAG_WIDTH;

    /**
     * 负值表示 偏左，正表示偏右
     */
    public  int horizontal ;
    /**
     * 负值表示 偏下，正，表示偏上
     */
    public   int vertical;

    public CameraBody() {}

    public CameraBody(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Override
    public void decoder(byte[] bytes) {
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 0, _byte, 0, 4);
        horizontal = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4, _byte, 0, 4);
        vertical = ByteUtil.bytesToInt(_byte);
    }

    @Override
    public int getBodyLength() {
        return LENGTH;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[LENGTH];
        System.arraycopy(ByteUtil.intToBytes(horizontal), 0, _byte, 0, 4);
        System.arraycopy(ByteUtil.intToBytes(vertical), 0, _byte, 4, 4);
        _byte[LENGTH-1]=messageEndFlag;
        return _byte;
    }
}
