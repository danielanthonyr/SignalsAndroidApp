package site.danielio.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import site.danielio.interactivestory.R;
import site.danielio.interactivestory.model.Page;
import site.danielio.interactivestory.model.Story;

public class StoryActivity extends AppCompatActivity {
    public static final String TAG = StoryActivity.class.getSimpleName();
    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        if(mName == null){
            mName = "Friend";
        }
        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);
     }

    private void loadPage(int choice)
    {
        mCurrentPage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());

        //Add name if placeholder included, wont add if no placeholder
        String pageText = mCurrentPage.getText();
        pageText = String.format(pageText, mName);

        mTextView.setText(pageText);

        if(mCurrentPage.isFinal()){
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play Again");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }else{
            mImageView.setImageDrawable(drawable);
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }


    }
}
