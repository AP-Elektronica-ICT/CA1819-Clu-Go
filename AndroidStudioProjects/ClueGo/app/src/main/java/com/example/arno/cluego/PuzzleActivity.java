package com.example.arno.cluego;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arno.cluego.Helpers.GestureDetectGridView;
import com.example.arno.cluego.Helpers.CustomAdapter;
import com.example.arno.cluego.Objects.Game;
import com.example.arno.cluego.Objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private static GestureDetectGridView mGridView;

    private static Game game = new Game();
    private static User usr = new User();

    private static int gameId;

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;
    private static Context mContext;
    public static int numberFoundClues;
    private static int mColumnWidth, mColumnHeight;

    public static String baseUrl, locName, uri;
    private static Drawable res1, res2, res3, res4, res5, res6, res7, res8, res9;

    private static ArrayList<String> puzzleNames = new ArrayList<String>(Arrays.asList("ap", "stadhuis", "ellerman", "puzzleclue"));

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    private static String[] tileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        mContext = this;

        baseUrl = getResources().getString(R.string.baseUrl);
        locName = getIntent().getStringExtra("locName");

        String usedPuzzle = puzzleNames.get((int) Math.random()*4+1);

        for (int i = 0; i < 10; i++) {
            uri = "@drawable/" + usedPuzzle + String.valueOf(i++);
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName()); //get image  resource

            switch (i){
                case 1:
                    res1 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 2:
                    res2 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 3:
                    res3 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 4:
                    res4 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 5:
                    res5 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 6:
                    res6 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 7:
                    res7 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 8:
                    res8 = mContext.getResources().getDrawable(imageResource, null);
                    break;
                case 9:
                    res9 = mContext.getResources().getDrawable(imageResource, null);
                    break;
            }
        }

        init();

        scramble();
        gameId = getIntent().getIntExtra("gameId", 0);

        usr = (User)getIntent().getSerializableExtra("userDataPackage");

        setDimensions();
    }

    private void init() {
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = String.valueOf(i);
        }
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackground(res1);
            else if (tileList[i].equals("1"))
                button.setBackground(res2);
            else if (tileList[i].equals("2"))
                button.setBackground(res3);
            else if (tileList[i].equals("3"))
                button.setBackground(res4);
            else if (tileList[i].equals("4"))
                button.setBackground(res5);
            else if (tileList[i].equals("5"))
                button.setBackground(res6);
            else if (tileList[i].equals("6"))
                button.setBackground(res7);
            else if (tileList[i].equals("7"))
                button.setBackground(res8);
            else if (tileList[i].equals("8"))
                button.setBackground(res9);

            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private static void swap(Context context, int currentPosition, int swap) {
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (!isSolved()) {
            String url = baseUrl + "game/" + gameId + "/" + locName + "/setfound";
            Log.d(TAG, "swap: " + url);
           StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("TAG", "onResponse: " + response);
                    if (response.contains("changed")) {
                        Toast.makeText(mContext, "Added new clue to your inventory!", Toast.LENGTH_SHORT).show();

                        final Intent i = new Intent(mContext, MainActivity.class);
                        i.putExtra("gameId", gameId);
                        mContext.startActivity(i);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int status = error.networkResponse.statusCode;
                    if (status == 400){
                        try {
                            String string = new String(error.networkResponse.data);
                            JSONObject object = new JSONObject(string);
                            if (object.has("message"))
                                Toast.makeText(mContext, object.get("message").toString(), Toast.LENGTH_SHORT).show();

                            else
                                Toast.makeText(mContext, object.get("error_description").toString(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                        }
                        final Intent i = new Intent(mContext, MainActivity.class);
                        i.putExtra("gameId", gameId);
                        mContext.startActivity(i);
                    }
                }
            });
            Volley.newRequestQueue(mContext).add(stringRequest);

        }
    }

    public void startMain(){

    }

    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }
}