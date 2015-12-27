package com.msx7.josn.tvconnection.action;

import com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/26.
 * {@link #isMute} true，表示静音，声音大小值无效
 * {@link #isSingle} true,并且非静音时，{@link #volL}表示左声道音量 {@link #volR}右声道音量
 * {@link #isSingle} fale,并且非静音时，{@link #volL} 表示当前音量
 */
public class VolBody implements MessageBody, IDecoder {
    public static final int LENGTH = 10+ MSG_END_FLAG_WIDTH;
    /**
     * true，表示静音
     * <br/>false 表示非静音
     */
    public boolean isMute;
    /**
     * false,表示双声道<br/>
     * true，表示左右声道可设置独立音量
     */
    public boolean isSingle;
    /**
     * 双声道时，此值表示音量<br/>
     * 左右声道分离时，此值表示左声道音量<br/>
     * -1 表示此值无效
     */
    public int volL=-1;
    /**
     * 双声道，此值无效<br/>
     * 左右声道分离时，此值表示右声道音量
     * -1 表示此值无效
     */
    public int volR=-1;

    public VolBody() {
    }

    public VolBody(boolean isMute, boolean isSingle) {
        this.isMute = isMute;
        this.isSingle = isSingle;
    }

    public VolBody(int vol) {
        this.volL = vol;
        isSingle = false;
    }

    public VolBody(int volL, int volR) {
        this.volL = volL;
        this.volR = volR;
        isSingle = true;
    }

    public VolBody(boolean isMute, boolean isSingle, int volL, int volR) {
        this.isMute = isMute;
        this.isSingle = isSingle;
        this.volL = volL;
        this.volR = volR;
    }

    @Override
    public void decoder(byte[] bytes) {
        isMute = bytes[0] == 0 ? true : false;
        isSingle = bytes[1] == 0 ? true : false;
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 2, _byte, 0, 4);
        volL = ByteUtil.bytesToInt(_byte);
        System.arraycopy(bytes, 6, _byte, 0, 4);
        volR = ByteUtil.bytesToInt(_byte);
    }

    @Override
    public int getBodyLength() {
        return LENGTH;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[LENGTH];
        if (isMute) _byte[0] = 0;
        else _byte[0] = 1;
        if (isSingle) _byte[1] = 0;
        else _byte[1] = 1;
        System.arraycopy(ByteUtil.intToBytes(volL), 0, _byte, 2, 4);
        System.arraycopy(ByteUtil.intToBytes(volR), 0, _byte, 6, 4);
        _byte[LENGTH-1]=messageEndFlag;
        return _byte;
    }


}
