package packt.java11.push;

public class ThrottleTool {

    static void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static long now() {
        return System.currentTimeMillis();
    }

    static Sleeper sleeper() {
        return new Sleeper();
    }

    static class Sleeper {
        private long start = now();

        void till(long time) {
            var timeNow = now() - start;
            if (timeNow > time) {
                return;
            }
            sleep(time - timeNow);
        }
    }
}
