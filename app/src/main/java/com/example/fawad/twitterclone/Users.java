package com.example.fawad.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;



public class Users extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    String followedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        final ListView list_view = findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,arrayList);
        list_view.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        list_view.setOnItemClickListener(this);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {

                if (list.size()>0 && e == null)
                {
                    for (ParseUser user: list)
                    {
                        arrayList.add(user.getUsername());
                    }
                    list_view.setAdapter(arrayAdapter);

                    // TODO TO SEE USER IS FOLLOWED ONE TIME AND SHOWN IN LIST AND ALSO DONT DOUBLE SHOW SAME NAME
                    for (String twitterUser: arrayList) {

                        if(ParseUser.getCurrentUser().getList("fanOf") != null) {
                        if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {

                            list_view.setItemChecked(arrayList.indexOf(twitterUser), true);
                            Toast.makeText(Users.this, ParseUser.getCurrentUser().getUsername() + "is followed" + followedUser, Toast.LENGTH_SHORT).show();
                        }
                      }
                    }

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.log_out:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        Toast.makeText(Users.this, "Successfull LogOut", Toast.LENGTH_SHORT).show();
                    }
                });
        break;
            case R.id.send_message:
                startActivity(new Intent(getApplicationContext(),SendingTweets.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked())
        {
            Toast.makeText(this, arrayList.get(position) + " is now Friend", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("fanOf",arrayList.get(position));
        }
        else
        {
            Toast.makeText(this, arrayList.get(position) + " is not Friend", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(arrayList.get(position));
            List currentUserList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserList);


        }

        //TODO TO SAVE VALUR IN PARSE SERVER BACKGROUND
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e ==  null)
                {
                    Toast.makeText(Users.this, " is followed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
