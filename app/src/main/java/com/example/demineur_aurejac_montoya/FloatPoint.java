package com.example.demineur_aurejac_montoya;

public class FloatPoint {
    float x,y;

    public FloatPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean xIsBeetween(FloatPoint leftPoint, FloatPoint rightPoint)
    {
        if(this.x >= leftPoint.x && this.x <= rightPoint.x)
        {
            return true;
        }
           return false;
    }
    public boolean yIsBeetween(FloatPoint topPoint, FloatPoint bottomPoint)
    {
        if(this.y >= topPoint.y && this.y <= bottomPoint.y)
        {
            return true;
        }
        return false;
    }
}
