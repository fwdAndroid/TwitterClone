package com.example.fawad.twitterclone;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendingTweets extends AppCompatActivity implements View.OnClickListener{

    EditText edtSend;
    Button btnSendTweet,btnViewOther;
    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_tweets);


        btnSendTweet = findViewById(R.id.btnSendTweet);
        btnViewOther = findViewById(R.id.btnViewOther);
        listView1 = findViewById(R.id.listView);
        edtSend = findViewById(R.id.edtSend);

        btnViewOther.setOnClickListener(this);
        btnSendTweet.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSendTweet:

                ParseObject po = new ParseObject("MyTweet");
                po.put("tweet",edtSend.getText().toString());
                po.put("user",ParseUser.getCurrentUser().getUsername());

                final ProgressDialog pd  = new ProgressDialog(this);
                pd.setMessage("Loading.......");

                po.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null)
                        {

                            Toast.makeText(SendingTweets.this, ParseUser.getCurrentUser().getUsername() + "'s tweet'" + "(" + edtSend.getText().toString() + ")" + "is Saved", Toast.LENGTH_SHORT).show();

                        }
                        
                        else 
                        {
                            Toast.makeText(SendingTweets.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                pd.dismiss();


                break;
            case R.id.btnViewOther:
                final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
                final SimpleAdapter  adapter = new SimpleAdapter(SendingTweets.this,tweetList,android.R.layout.simple_list_item_2, new String[]{"TweetUserName","TweetUserValue"}, new int[] {android.R.id.text1,android.R.id.text2});
                ParseQuery pq =  ParseQuery.getQuery("MyTweet");
                pq.whereContainedIn("user",ParseUser.getCurrentUser().getList("fanOf"));
                pq.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (objects.size() > 0 && e == null)
                        {
                            for (ParseObject tweetObj : objects)
                            {
                                HashMap<String,String> userTweet = new HashMap<>();
                                userTweet.put("TweetUserName",tweetObj.getString("user"));
                                userTweet.put("TweetUserValue",tweetObj.getString("tweet"));
                                tweetList.add(userTweet);
                            }

                            listView1.setAdapter(adapter);
                        }

                    }

                });


                break;
        }
    }
}
