package com.example.vitaly.colorpicker;

import android.content.res.Resources;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    private static int SQUARE_SIDE = 200;
    private static int SQUARE_MARGIN = 100;
    private static String CHOSEN_COLOR = "CHOSEN_COLOR";


    private ImageView chosenColor;
    private int currentColor;
    private List<ColorSquare> colorSquares;
    private ConstraintLayout colorPicker;
    private int[] colors;
    private TextView colorText;


    private int[] buildHueColorArray() {
        int[] hue = new int[361];
        int count = 0;
        for (int i = hue.length - 1; i >= 0; i--, count++)
        {
            hue[count] = Color.HSVToColor(new float[]{i, 1f, 1f});
        }
        return hue;
    }

    private int getColor(ColorSquare colorSquare) {
        double width = (colorSquares.size() - 1) * SQUARE_MARGIN + colorSquares.size() * SQUARE_SIDE;
        int colorIndex =  colors.length - (int) ((((double) colorSquare.getCenterX()) / (double) width) * colors.length);
        return  colors[colorIndex];
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CHOSEN_COLOR, currentColor);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentColor = savedInstanceState.getInt(CHOSEN_COLOR);
        chosenColor.setBackgroundColor(currentColor);
        colorText.setText(getColorString(currentColor));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorText = findViewById(R.id.text_image);
        chosenColor = findViewById(R.id.choosen_color);
        colorSquares = new ArrayList<>();
        colors = buildHueColorArray();
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                colors);

        colorPicker = findViewById(R.id.color_picker);
        colorPicker.setBackgroundDrawable(gradientDrawable);

        createColorSquares();
        setColorsAndListeners(colorSquares);
    }


    private void createColorSquares() {
        for (int i = 0; i < 16; i++) {
            int idNew = i + 1;
            int centerX = (idNew - 1) * SQUARE_SIDE + (idNew - 1) * SQUARE_MARGIN + SQUARE_SIDE / 2;
            ColorSquare colorSquare = new ColorSquare(this, centerX);
            colorSquare.setImageDrawable(getResources().getDrawable(R.drawable.square_bordered));

            colorSquare.setId(idNew);
            ConstraintLayout.LayoutParams paramsNew = new ConstraintLayout.LayoutParams(SQUARE_SIDE, SQUARE_SIDE);
            paramsNew.endToStart = idNew - 1;

            if (i != 0) {
                paramsNew.setMarginStart(SQUARE_MARGIN);
                paramsNew.setMarginEnd(SQUARE_MARGIN);
            }

            colorSquare.setLayoutParams(paramsNew);
            colorPicker.addView(colorSquare, paramsNew);
            colorSquares.add(colorSquare);
        }
    }


    private String getColorString(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        float[] hsv = new float[3];
        Color.RGBToHSV(red, green, blue, hsv);

        return "rgb\n" + red + " : " + green + " : " + blue + "\n\n" +
                "hsv\n" + hsv[0] + " : " + hsv[1] + " : " + hsv[2];

    }

    private void setColorsAndListeners(List<ColorSquare> colorSquares) {
        for (ColorSquare colorSquare : colorSquares) {
            final int color = getColor(colorSquare);
            colorSquare.setBackgroundColor(color);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chosenColor.setBackgroundColor(color);
                    currentColor = color;
                    colorText.setText(getColorString(color));
                }
            };
            colorSquare.setOnClickListener(listener);
        }
    }
}
