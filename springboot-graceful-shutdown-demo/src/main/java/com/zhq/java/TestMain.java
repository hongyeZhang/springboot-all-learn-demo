package com.zhq.java;

public class TestMain {

    private ShutdownHook shutdownHook;

    public TestMain() {
        this.shutdownHook = new ShutdownHook(Thread.currentThread());
    }

    public void execute() {
        while (!shutdownHook.shouldShutDown()) {
            System.out.println("I am sleep");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                System.out.println("execute() interrupted");
            }
            System.out.println("I am not sleep");
        }
        System.out.println("end of execute()");
    }

    /**
     * 通过  kill -2 PID 进行测试
     * @param args
     */
    public static void main(String[] args) {
        TestMain app = new TestMain();
        System.out.println("Hello World!");
        app.execute();
        System.out.println("End of main()");
    }
}
