package com.example.demineur_aurejac_montoya.UI.Options;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.demineur_aurejac_montoya.R;


//fenêtre de selection d'une durée temporelle (mminutes et secondes)
public class TimePickerDialog extends Dialog {


        private OptionsActivity activity;
        private Dialog dialog;
        public Button validerButton;
        private boolean isWon;
        private NumberPicker secondsPicker,minutesPicker;


        public TimePickerDialog(OptionsActivity activity) {
            super(activity);

            this.activity = activity;
        }

    public TimePickerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_time_picker);


            validerButton = (Button) findViewById(R.id.valider_button);
            secondsPicker = (NumberPicker) findViewById(R.id.seconds_picker) ;
            minutesPicker = (NumberPicker) findViewById(R.id.minutes_picker) ;
            validerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setTime(minutesPicker.getValue(),secondsPicker.getValue());
                    dismiss();
                }
            });
            secondsPicker.setMinValue(0);
            minutesPicker.setMinValue(0);
            secondsPicker.setMaxValue(59);
            minutesPicker.setMaxValue(59);
        }



    }
