import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeWheel {
    private static final int START_SIZE = 64;
    private Object[] ringBuffer;
    private int buffSize;
    private ExecutorService executorService;
    private AtomicInteger taskSize = new AtomicInteger();

    private volatile boolean stop = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public TimeWheel(ExecutorService executorService) {
        this.executorService = executorService;
        ringBuffer = new Object[START_SIZE];
        this.buffSize = START_SIZE;
    }

}
