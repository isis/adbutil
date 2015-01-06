package adbutil.isisredirect.co.jp.adbutil.adblibs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by k-ishida on 2014/12/31.
 */
public class ADBUtiles {

    public static final String TAG = ADBUtiles.class.getSimpleName();


    static public void clearLogCat() {
        try {
            Runtime.getRuntime().exec("logcat -c");
        } catch (Exception e) {
        }
    }

    static public void saveLogCat(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            OutputStream o = null;
            try {
                o = new FileOutputStream(file);
                doLogcat(o);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (o != null) {
                    try {
                        o.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static public void doADBRestart(final OutputStream os) throws IOException {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    {
                        //Log.d(TAG, "kill-server");
                        ArrayList<String> commandLine = new ArrayList<String>();
                        commandLine.add("adb");
                        commandLine.add("kill-server");
                        try {
                            processCommand(os, commandLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                        }

                    }
                    synchronized (this) {
                        try {
                            wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    {
                        //Log.d(TAG, "start-server");
                        ArrayList<String> commandLine = new ArrayList<String>();
                        commandLine.add("adb");
                        commandLine.add("start-server");
                        try {
                            ADBUtiles.processServerCommand(os, commandLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    static public void doADBDevices(final OutputStream os) throws IOException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<String> commandLine = new ArrayList<String>();
                commandLine.add("adb");
                commandLine.add("devices");
                try {
                    processServerCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    static public void doADBConnect(final OutputStream os) throws IOException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("connect");
                    commandLine.add("127.0.0.1:5555");
                    processCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doADBDisconnect(final OutputStream os) throws IOException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("disconnect");
                    commandLine.add("127.0.0.1:5555");
                    processCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doADBSHELLLs(final OutputStream os) throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("shell");
                    commandLine.add("ls");
                    commandLine.add("-al");
                    processCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doLogcat(final OutputStream os) throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("shell");
                    commandLine.add("logcat");
                    commandLine.add("-d");
                    commandLine.add("-v");
                    commandLine.add("long");
                    processServerCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doADBUSB(final OutputStream os) throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("usb");
                    processCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doReboot(final OutputStream os) throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<String> commandLine = new ArrayList<String>();
                    commandLine.add("adb");
                    commandLine.add("reboot");
                    processCommand(os, commandLine);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };
        thread.start();
    }

    static public void doPrintenv(final OutputStream os) throws IOException {
        try {
            ArrayList<String> commandLine = new ArrayList<String>();
            commandLine.add("printenv");
            processCommand(os, commandLine);
        } catch (IOException e) {
            throw e;
        } finally {
        }

    }

    static public void doId(final OutputStream os) throws IOException {
        try {
            ArrayList<String> commandLine = new ArrayList<String>();
            commandLine.add("id");
            processCommand(os, commandLine);
        } catch (IOException e) {
            throw e;
        } finally {
        }

    }

    static private String homepath = "";


    private static void processServerCommand(OutputStream os, ArrayList<String> commandLine) throws IOException {
        try {
            ProcessBuilder pb = new ProcessBuilder(commandLine);

            String path = System.getenv("PATH");
            Map<String, String> env = pb.environment();
            env.put("PATH", path);
            env.put("HOME", homepath);

            pb.redirectErrorStream(true);

            pb.directory(new File(homepath));

            Process process = pb.start();
            Thread th_input = new OutputThread(os, process);
            th_input.start();

             try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

        }
    }


    private static void processCommand(OutputStream os, ArrayList<String> commandLine) throws IOException {
        Process process = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(commandLine);

            String path = System.getenv("PATH");
            Map<String, String> env = pb.environment();
            env.put("PATH", path);
            env.put("HOME", homepath);

            pb.redirectErrorStream(true);

            pb.directory(new File(homepath));

            process = pb.start();
            Thread th_input = new OutputThread(os, process);
            th_input.start();

            process.waitFor();
            th_input.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    static public String getHomepath() {
        return homepath;
    }

    static public void setHomepath(String homepath) {
        ADBUtiles.homepath = homepath;
    }

    static protected class OutputThread extends Thread {

        protected OutputStream os = null;
        protected Process process = null;

        private OutputThread() {
            super();
        }

        public OutputThread(OutputStream os, Process process) {
            this();
            this.os = os;
            this.process = process;

        }

        @Override
        public void run() {
            byte buffer[] = new byte[1024];
            try {
                InputStream is = process.getInputStream();
                int length = 0;
                while ((length = is.read(buffer)) >= 0) {
                    os.write(buffer, 0, length);
                }
                os.write('\n');
                os.write('\n');
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                buffer = null;
            }
        }
    }

}
