package com.mauriciotogneri.escape.activities;

import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mauriciotogneri.androidutils.Screen;
import com.mauriciotogneri.androidutils.Screen.ScreenSize;
import com.mauriciotogneri.escape.R;

public class MainActivity extends AppCompatActivity
{
    private static final int TILES_WIDTH = 24;
    private static final int TILES_HEIGHT = 14;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        addTiles();
    }

    private void addTiles()
    {
        Screen screen = new Screen(this);
        ScreenSize size = screen.size();

        int tileHeight = size.height() / TILES_HEIGHT;
        int tileWidth = size.width() / TILES_WIDTH;
        int tileSizePx = Math.min(tileHeight, tileWidth);

        ViewGroup root = findViewById(R.id.container);
        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < TILES_HEIGHT; i++)
        {
            LinearLayout row = new LinearLayout(this);

            for (int j = 0; j < TILES_WIDTH; j++)
            {
                TextView tile = (TextView) inflater.inflate(R.layout.tile, row, false);
                tile.getLayoutParams().width = tileSizePx;
                tile.getLayoutParams().height = tileSizePx;

                row.addView(tile);
            }

            root.addView(row);
        }
    }

    private void enableFullScreen(Window window)
    {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT)
        {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        else if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
        {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        enableFullScreen(getWindow());
    }
}