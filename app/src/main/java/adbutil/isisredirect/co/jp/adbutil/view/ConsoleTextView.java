package adbutil.isisredirect.co.jp.adbutil.view;

import android.os.Handler;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by k-ishida on 2014/12/31.
 */
public class ConsoleTextView extends OutputStream {

    public static final String TAG = ConsoleTextView.class.getSimpleName();

    final Handler h = new Handler();

    protected TextView textview;
    protected StringBuffer sbuffer;
    BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    Thread thread;

    public ConsoleTextView(TextView v) {
        super();
        sbuffer = new StringBuffer();
        this.textview = v;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String s = queue.take();
                        h.post(new OutputHandler(s));
                        synchronized (this) {
                            wait(1);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };
        thread.start();
    }

    class OutputHandler implements Runnable {

        String s;

        OutputHandler(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            textview.append(s);
        }
    }

    protected void showText(String s) {
        queue.add(s);
    }


    @Override
    public void write(int oneByte) throws IOException {
        synchronized (this) {
            sbuffer.append((char) oneByte);
            if (oneByte == '\n' || oneByte == '\r') {
                showText(sbuffer.toString());
                sbuffer.setLength(0);
            }
        }
    }

    public void clear() {
        textview.setText("");
        sbuffer.setLength(0);
    }

    public void destory() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }
}
