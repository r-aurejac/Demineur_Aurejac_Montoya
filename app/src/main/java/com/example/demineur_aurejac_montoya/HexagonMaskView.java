package com.example.demineur_aurejac_montoya;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

public class HexagonMaskView extends androidx.appcompat.widget.AppCompatImageView {
    private Path hexagonPath;
    private Path hexagonBorderPath;
    private Paint mBorderPaint;
    int width ;
    int height ;

    public HexagonMaskView(Context context) {
        super(context);
        init();
    }

    public HexagonMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.hexagonPath = new Path();
        this.hexagonBorderPath = new Path();

        this.mBorderPaint = new Paint();
        this.mBorderPaint.setColor(Color.WHITE);
        this.mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBorderPaint.setStrokeWidth(50f);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    public void setRadius(float radius) {
        calculatePath(radius);
    }

    public void setBorderColor(int color) {
        this.mBorderPaint.setColor(color);
        invalidate();
    }

    private void calculatePath(float radius) {
        float halfRadius = radius / 2f;
        float triangleHeight = (float) (Math.sqrt(3.0) * halfRadius);
        float centerX = getMeasuredWidth() / 2f;
        float centerY = getMeasuredHeight() / 2f;
        float dif = radius - triangleHeight/2;

        // traçage de l'hexagone de la vue

        this.hexagonPath.reset();
        // inferieur gauche
        this.hexagonPath.moveTo(centerX -  dif, centerY + triangleHeight);
        // centre gauche
        this.hexagonPath.lineTo(centerX - radius, centerY );
        // superieur gauche
        this.hexagonPath.lineTo(centerX- dif , centerY - triangleHeight);
        // superieur droit
        this.hexagonPath.lineTo(centerX + dif, centerY - triangleHeight);
        // centre droit
        this.hexagonPath.lineTo(centerX + radius, centerY );
        // inferieur droit
        this.hexagonPath.lineTo(centerX + dif, centerY + triangleHeight);
        this.hexagonPath.close();

        // tracage des bordure

        float radiusBorder = radius - 5f;
        float halfRadiusBorder = radiusBorder / 2f;
        float triangleBorderHeight = (float) (Math.sqrt(3.0) * halfRadiusBorder);
        float difBorder = radiusBorder - triangleBorderHeight/2;

        this.hexagonBorderPath.reset();
        // inferieur gauche
        this.hexagonBorderPath.moveTo(centerX -  difBorder, centerY + triangleBorderHeight);
        // centre gauche
        this.hexagonBorderPath.lineTo(centerX - radiusBorder, centerY );
        // superieur gauche
        this.hexagonBorderPath.lineTo(centerX- difBorder, centerY - triangleBorderHeight);
        // superieur droit
        this.hexagonBorderPath.lineTo(centerX +difBorder, centerY - triangleBorderHeight);
        // centre droit
        this.hexagonBorderPath.lineTo(centerX + radiusBorder, centerY );
        // inferieur droit
        this.hexagonBorderPath.lineTo(centerX + difBorder, centerY + triangleBorderHeight);
        this.hexagonBorderPath.close();
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent( MotionEvent event ) {

            float radius = Math.min(width / 2f, height / 2f) - 10f;
            float halfRadius = radius / 2f;
            float triangleHeight = (float) (Math.sqrt(3.0) * halfRadius);
            float centerX = getMeasuredWidth() / 2f;
            float centerY = getMeasuredHeight() / 2f;


            FloatPoint botPoint = new FloatPoint(centerX, centerY + radius);
            FloatPoint leftBotPoint  = new FloatPoint(centerX - triangleHeight, centerY + halfRadius);
            FloatPoint leftTopPoint = new FloatPoint(centerX - triangleHeight, centerY - halfRadius);
            FloatPoint topPoint = new FloatPoint(centerX, centerY - radius);
            FloatPoint rightTopPoint= new FloatPoint(centerX + triangleHeight, centerY - halfRadius);
            FloatPoint rightBotPoint = new FloatPoint(centerX + triangleHeight, centerY + halfRadius);
            float x =  event.getX();
            float y =  event.getY();
            FloatPoint clickPoint = new FloatPoint(x,y);

            /*
            if(clickPoint.xIsBeetween(leftTopPoint,rightTopPoint)&&clickPoint.yIsBeetween(leftTopPoint,leftBotPoint))
                return super.onTouchEvent(event);
                else return false;

             */
            if(isInsideHexagon(centerX,centerY,radius*2,x,y))
                return super.onTouchEvent(event);
            else return false;

    }
    public boolean isInsideHexagon(float x0, float y0, float d, float x, float y) {
        float dx = Math.abs(x - x0)/d;
        float dy = Math.abs(y - y0)/d;
        double a = 0.25 * Math.sqrt(3.0);
        return (dy <= a) && (a*dx + 0.25*dy <= 0.5*a);
    }

    @Override
    public void onDraw(Canvas c) {

        // dessin de l'hexagone à partir du tarçage

        c.drawPath(hexagonBorderPath, mBorderPaint);
        c.clipPath(hexagonPath, Region.Op.INTERSECT);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        super.onDraw(c);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        calculatePath(Math.min(width / 2f, height / 2f) - 10f);
    }



}
