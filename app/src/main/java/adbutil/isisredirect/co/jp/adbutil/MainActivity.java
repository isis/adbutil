package adbutil.isisredirect.co.jp.adbutil;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import adbutil.isisredirect.co.jp.adbutil.adblibs.ADBUtiles;
import adbutil.isisredirect.co.jp.adbutil.view.ConsoleTextView;


public class MainActivity extends Activity {

    protected ConsoleTextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.consoleTextView);
        outputTextView = new ConsoleTextView(tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String homepath = getDir("HOME", MODE_PRIVATE).getAbsolutePath();
        ADBUtiles.setHomepath(homepath);
        {
            Button btn = (Button) findViewById(R.id.adbrestart);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doADBRestart(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.adbdevices);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doADBDevices(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//           {
//                Button btn = (Button)findViewById(R.id.adbconnect);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            ADBUtiles.doADBConnect(outputTextView);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//            {
//                Button btn = (Button)findViewById(R.id.adbdisconnect);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            ADBUtiles.doADBDisconnect(outputTextView);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
        {
            Button btn = (Button) findViewById(R.id.logcatdump);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doLogcat(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.logcatc);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADBUtiles.clearLogCat();
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.adbshells);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doADBSHELLLs(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.adbusb);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doADBUSB(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.reboot);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ADBUtiles.doReboot(outputTextView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        {
            Button btn = (Button) findViewById(R.id.clear);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    outputTextView.clear();
                }
            });
        }
//        {
//            Button btn = (Button)findViewById(R.id.printenv);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        ADBUtiles.doPrintenv(outputTextView);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        {
//            Button btn = (Button)findViewById(R.id.id_command);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        ADBUtiles.doId(outputTextView);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
    }

    @Override
    protected void onDestroy() {
        outputTextView.destory();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
