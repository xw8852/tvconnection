package scret.com.msx7.josn.tvconnection.action;

import scret.com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import scret.com.msx7.josn.tvconnection.pack.IDecoder;
import scret.com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * 左右独立声道设置音量
 * Created by Josn on 2015/12/21.
 */
public class SingleVolBody implements MessageBody, IDecoder {
    int left;
    int right;

    @Override
    public void decoder(byte[] bytes) {
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 0, _byte, 0, 4);
        left = ByteUtil.bytesToInt(bytes);
        System.arraycopy(bytes, 4, _byte, 0, 4);
        right = ByteUtil.bytesToInt(bytes);
    }

    @Override
    public int getBodyLength() {
        return 8;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[8];
        System.arraycopy(ByteUtil.intToBytes(left), 0, _byte, 0, 4);
        System.arraycopy(ByteUtil.intToBytes(right), 0, _byte, 4, 4);
        return _byte;
    }

}
