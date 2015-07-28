package androidparallelasynctask.bij.com.androidparallelasynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Button buttonStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    MyAsyncTask asyncTask1, asyncTask2, asyncTask3, asyncTask4, asyncTask5;
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar1 = (ProgressBar)findViewById(R.id.progressbar1);
        progressBar2 = (ProgressBar)findViewById(R.id.progressbar2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressbar3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressbar4);
        progressBar5 = (ProgressBar)findViewById(R.id.progressbar5);

        buttonStart = (Button)findViewById(R.id.start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeMyNewthread();
            }
        });
        //Toast.makeText(this,String.valueOf(NUMBER_OF_CORES),Toast.LENGTH_SHORT).show();
    }


    private void executeMythread()
    {
        asyncTask1 = new MyAsyncTask(progressBar1);
        asyncTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //asyncTask1.execute();
        asyncTask2 = new MyAsyncTask(progressBar2);
        // asyncTask2.execute();
        asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        asyncTask3 = new MyAsyncTask(progressBar3);
        // asyncTask3.execute();
        asyncTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        asyncTask4 = new MyAsyncTask(progressBar4);
        asyncTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        asyncTask5 = new MyAsyncTask(progressBar5);
        asyncTask5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    private void executeMyNewthread()
    {

        int i=1;
        Log.e("Level", "Started");
        for(i=1;i<50;i++ )
            new MyNewAsyncTask(i).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


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
    public class MyAsyncTask extends AsyncTask<Void,Integer,Void>
    {

        ProgressBar myProgressBar;
        public MyAsyncTask(ProgressBar target) {
            myProgressBar = target;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i=0; i<100; i++){
                //publishProgress(i);
                SystemClock.sleep(100);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           // myProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    public class MyNewAsyncTask extends AsyncTask<Void,Integer,Void>
    {

        int _lavel=0;
        public MyNewAsyncTask(int lavel) {
            _lavel = lavel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i=0; i<100; i++){
                //publishProgress(i);
                SystemClock.sleep(100);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // myProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("Level", String.valueOf(_lavel));
            Toast.makeText(MainActivity.this,"Level "+String.valueOf(_lavel),Toast.LENGTH_SHORT).show();
        }
    }
}
