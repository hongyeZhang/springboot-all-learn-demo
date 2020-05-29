package com.zhq.java;

public class ShutdownHook extends Thread {
    private Thread mainThread;
    private boolean shutDownSignalReceived;

    @Override
    public void run() {
        System.out.println("Shut down signal received.");
        this.shutDownSignalReceived = true;
        mainThread.interrupt();
        try {
            mainThread.join(); //当收到停止信号时，等待mainThread的执行完成
        } catch (InterruptedException e) {
        }
        System.out.println("Shut down complete.");
    }

    public ShutdownHook(Thread mainThread) {
        super();
        this.mainThread = mainThread;
        this.shutDownSignalReceived = false;
//        其中关键语句Runtime.getRuntime().addShutdownHook(this);，注册一个JVM关闭的钩子，这个钩子可以在以下几种场景被调用：
//        程序正常退出
//        使用System.exit()
//        终端使用Ctrl+C触发的中断
//        系统关闭
//        使用Kill pid命令干掉进程
        Runtime.getRuntime().addShutdownHook(this);
    }

    public boolean shouldShutDown() {
        return shutDownSignalReceived;
    }

}
