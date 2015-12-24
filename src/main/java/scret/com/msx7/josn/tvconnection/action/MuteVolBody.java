package scret.com.msx7.josn.tvconnection.action;

import scret.com.msx7.josn.tvconnection.pack.IDecoder;
import scret.com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * 音量静音
 * Created by Josn on 2015/12/21.
 */
public class MuteVolBody implements MessageBody, IDecoder {
    /**
     * 1 表示静音
     * 非1：表示不静音
     */
    boolean isMute;

    @Override
    public void decoder(byte[] bytes) {
        isMute = bytes[0] == 1;
    }

    @Override
    public int getBodyLength() {
        return 1;
    }

    @Override
    public byte[] encode() {
        return isMute ? new byte[]{1} : new byte[]{0};
    }
}
