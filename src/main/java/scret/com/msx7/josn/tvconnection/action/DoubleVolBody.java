package scret.com.msx7.josn.tvconnection.action;


import scret.com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import scret.com.msx7.josn.tvconnection.pack.IDecoder;
import scret.com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * 双声道设置音量
 * Created by Josn on 2015/12/21.
 */
public class DoubleVolBody implements MessageBody, IDecoder {
    int vol;

    @Override
    public void decoder(byte[] bytes) {
        vol = ByteUtil.bytesToInt(bytes);
    }

    @Override
    public int getBodyLength() {
        return 4;
    }

    @Override
    public byte[] encode() {
        return ByteUtil.intToBytes(vol);
    }
}
