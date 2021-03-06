package com.msx7.josn.tvconnection.pack;

/**
 * Created by Josn on 2015/12/6.
 * 所有功能的编码
 * 在包里面预留长度为4位即0-9999
 */
public class Code {
    /**
     * 心跳包
     */
    public static final int BASE_CODE = 0X0001;
    /**
     * 0X400以后才是具体功能编码 前面400预留
     */

    /***
     * 400段 预留为屏幕点击滑动时间功能标号段
     */
    /**
     * 触摸动作 -按下
     */
    public static final int ACTION_EVENT_DOWN = 0X401;
    /**
     * 触摸动作 -移动
     */
    public static final int ACTION_EVENT_MOVE = 0X402;
    /**
     * 触摸动作 -弹起
     */
    public static final int ACTION_EVENT_UP = 0X403;
    /**
     * 单击
     */
    public static final int ACTION_TAP = 0X404;
    /**
     * 双击
     */
    public static final int ACTION_DOUBLE_TAP = 0X405;

    /**
     * 获取触屏信息
     */
    public static final int ACTION_MOTION_GET = 0X406;
    /**
     * 设置触屏事件
     */
    public static final int ACTION_MOTION_SET = 0X407;

    /**
     * 声音相关处理功能 编码段为 500
     */
    /**
     * 设置当前音量信息
     */
    public static final int ACTION_VOL_SET = 0X501;

    /**
     * 获取当前音量信息
     */
    public static final int ACTION_VOL_GET = 0X502;

    /**
     * 摄影、相机相关功能 编码段  600
     */

    /**
     * 打开相机
     */
    public static final int ACTION_OPEN_CAMERA = 0X601;
    /**
     * 拍照
     */
    public static final int ACTION_TAKE_PHTOTO = 0X602;
    /**
     * 打开摄像
     */
    public static final int ACTION_OPEN_VEDIO_RECORD = 0X603;

    /**
     * 开始摄影
     */
    public static final int ACTION_START_VEDIO_RECORD = 0X604;

    /**
     * 暂停摄影
     */
    public static final int ACTION_PAUSE_VEDIO_RECORD = 0X605;
    /**
     * 结束摄影
     */
    public static final int ACTION_STOP_VEDIO_RECORD = 0X606;
    /**
     * 旋转相机
     */
    public static final int ACTION_CAMERA_ANGLE_ROTATE = 0X607;
    /**
     * 获取相机位置
     */
    public static final int ACTION_CAMERA_ANGLE_GET = 0X608;

    /**
     * 系统功能键预留
     */
    /**
     * 菜单键
     */
    public static final int ACTION_SYSTEM_MENU = 0x701;
    /**
     * home键
     */
    public static final int ACTION_SYSTEM_HOME = 0x702;
    /**
     * 返回键
     */
    public static final int ACTION_SYSTEM_BACK = 0x703;
    /**
     * 话筒
     */
    public static final int ACTION_MICROPHONE = 0x704;
    /**
     * 远程监控
     */
    public static final int ACTION_MONITORING = 0x705;
}
