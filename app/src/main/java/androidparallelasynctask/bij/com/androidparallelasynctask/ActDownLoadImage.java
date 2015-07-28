package androidparallelasynctask.bij.com.androidparallelasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ActDownLoadImage extends ActionBarActivity {

    /* Local Variables*/
    Button buttonStart, butShowNextImage;
    ProgressBar progressBar1;
    List<Bitmap> imagesStack = new ArrayList<Bitmap>();

    ImageView imageview;
    int index = 0, MY_THREAD_SIZE = 10,THREAD_TIME_OUT=1500 ;


    /* String array for urls*/
    String[] imageUrls = new String[]
            {"http://shreyanarayan.com/images/gallery/big20.jpg",
                    "http://shreyanarayan.com/images/gallery/big19.jpg",
                    "http://thumbs.dreamstime.com/x/confused-young-man-black-t-shirt-19865275.jpg",
                    "http://freethumbs.dreamstime.com/6/big/free_66136.jpg",
                    "http://freethumbs.dreamstime.com/21/big/free_216400.jpg",
                    "http://freethumbs.dreamstime.com/25/big/free_254254.jpg",
                    "http://thumbs.dreamstime.com/z/transgender-sepia-vintage-portrait-skinhead-woman-false-whiskers-imperial-beard-55808153.jpg",
                    "http://thumbs.dreamstime.com/x/woman-mask-12192976.jpg",
                    "http://thumbs.dreamstime.com/z/tenderness-3253820.jpg",
                    "http://images.freeimages.com/images/previews/fd5/pink-flower-1374766.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_download_images);
        imagesStack.clear();//Clear list

        progressBar1 = (ProgressBar) findViewById(R.id.progressbar1);
        imageview = (ImageView) findViewById(R.id.imageviewNew);
        butShowNextImage = (Button) findViewById(R.id.butShowNextImage);

        buttonStart = (Button) findViewById(R.id.start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runAsyncThread();
            }
        });

        butShowNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadedImage();
            }
        });
    }

    private void showDownloadedImage() {

        try {
            imageview.setImageBitmap(imagesStack.get(index));
        } catch (Exception e) {

        }
        if (index < imagesStack.size())
            index++;
        else
            index = 0;

    }

 /*
  This function is used to run parallel Asyn task,
  to download images from server
  */
    private void runAsyncThread() {

        int i = 0;
        progressBar1.setVisibility(View.VISIBLE);
        for (i = 0; i < MY_THREAD_SIZE; i++)
                new DownloadImageAsyn(imageUrls[i]).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

    /*
      This Asyn class is used to download image from server
     */
    public class DownloadImageAsyn extends AsyncTask<Void, Integer, Bitmap> {
        String imageURL = "";

        public DownloadImageAsyn( String imgUrl) {
           imageURL = imgUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(Void... params) {


            //Here i am setting time out for this thread
            try {
                get(THREAD_TIME_OUT, TimeUnit.MILLISECONDS);
            }
            catch(Exception e)
            {

            }

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            imagesStack.add(result);
            progressBar1.setProgress(imagesStack.size());

            if (imagesStack.size() == MY_THREAD_SIZE) {
                progressBar1.setVisibility(View.GONE);//Disable progressbar
                butShowNextImage.setEnabled(true);//Enable image button
                showDownloadedImage();

            }

        }
    }
}
