package com.msx7.josn.tvconnection.action;


import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;
import com.msx7.josn.tvconnection.mima.common.util.ByteUtil;

/**
 * Created by Josn on 2015/12/21.
 */
public class CameraGetBody implements MessageBody, IDecoder {
    public static final int LENGTH = 4 * 6 + MSG_END_FLAG_WIDTH;

    /**
     * 负值表示 偏左，正表示偏右
     */
    public  int horizontal;
    /**
     * 负值表示 偏下，正，表示偏上
     */
  public   int vertical;
    public int limitLeft;
    public  int limitRight;
    public int limitTop;
    public int limitBottom;

    public CameraGetBody() {
    }

    public CameraGetBody(int horizontal, int vertical, int limitLeft, int limitBottom, int limitRight, int limitTop) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.limitLeft = limitLeft;
        this.limitRight = limitRight;
        this.limitTop = limitTop;
        this.limitBottom = limitBottom;
    }

    @Override
    public void decoder(byte[] bytes) {
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 4 * 0, _byte, 0, 4);
        horizontal = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4 * 1, _byte, 0, 4);
        vertical = ByteUtil.bytesToInt(_byte);

        System.arraycopy(bytes, 4 * 2, _byte, 0, 4);
        limitLeft = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4 * 3, _byte, 0, 4);
        limitRight = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4 * 4, _byte, 0, 4);
        limitTop = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 4 * 5, _byte, 0, 4);
        limitBottom = ByteUtil.bytesToInt(_byte);
    }

    @Override
    public int getBodyLength() {
        return LENGTH;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[LENGTH];
        System.arraycopy(ByteUtil.intToBytes(horizontal), 0, _byte, 4 * 0, 4);
        System.arraycopy(ByteUtil.intToBytes(vertical), 0, _byte, 4 * 1, 4);
        System.arraycopy(ByteUtil.intToBytes(limitLeft), 0, _byte, 4 * 2, 4);
        System.arraycopy(ByteUtil.intToBytes(limitRight), 0, _byte, 4 * 3, 4);
        System.arraycopy(ByteUtil.intToBytes(limitTop), 0, _byte, 4 * 4, 4);
        System.arraycopy(ByteUtil.intToBytes(limitBottom), 0, _byte, 4 * 5, 4);

        _byte[LENGTH - 1] = messageEndFlag;
        return _byte;
    }
}
