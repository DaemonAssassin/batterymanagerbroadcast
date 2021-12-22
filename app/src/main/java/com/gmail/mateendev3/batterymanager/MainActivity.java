package com.gmail.mateendev3.batterymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvBatteryHealth, tvBatteryPercent, tvBatteryTimeToCharge;
    ImageView ivBatteryStatus;
    BatteryInfoReceiver mBatteryInfoReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBatteryHealth = findViewById(R.id.tv_battery_health);
        tvBatteryPercent = findViewById(R.id.tv_battery_percent);
        tvBatteryTimeToCharge = findViewById(R.id.tv_battery_status);
        ivBatteryStatus = findViewById(R.id.iv_ic_battery);

        mBatteryInfoReceiver = new BatteryInfoReceiver(
                tvBatteryHealth,
                tvBatteryPercent,
                tvBatteryTimeToCharge,
                ivBatteryStatus
        );

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(mBatteryInfoReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBatteryInfoReceiver);
        super.onDestroy();
    }

    public static final class BatteryInfoReceiver extends BroadcastReceiver {
        private final TextView tvBatteryHealth, tvBatteryPercent, tvBatteryTimeToCharge;
        private final ImageView ivBatteryStatus;

        public BatteryInfoReceiver(TextView tvBatteryHealth, TextView tvBatteryPercent, TextView tvBatteryTimeToCharge, ImageView ivBatteryStatus) {
            this.tvBatteryHealth = tvBatteryHealth;
            this.tvBatteryPercent = tvBatteryPercent;
            this.tvBatteryTimeToCharge = tvBatteryTimeToCharge;
            this.ivBatteryStatus = ivBatteryStatus;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            int isPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int batteryPercent = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);

            tvBatteryPercent.setText(Integer.toString(batteryPercent));
            setBatteryHealth(batteryHealth);
            setBatteryPercentAndPlugged(isPlugged, batteryPercent);
            setBatteryStatus(batteryStatus);


        }

        private void setBatteryStatus(int batteryStatus) {
            if (batteryStatus == 1)
                tvBatteryTimeToCharge.setText("Unknown");
            else if (batteryStatus == 2)
                tvBatteryTimeToCharge.setText("Charging");
            else if (batteryStatus == 3)
                tvBatteryTimeToCharge.setText("Discharging");
            else if (batteryStatus == 4)
                tvBatteryTimeToCharge.setText("Not Charging");
            else
                tvBatteryTimeToCharge.setText("Full");
        }

        private void setBatteryPercentAndPlugged(int isPlugged, int batteryPercent) {
            if (batteryPercent >= 0 && batteryPercent < 20 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_zero_no_charging);
            else if (batteryPercent >= 20 && batteryPercent < 40 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_zero_20_more_charging);
            else if (batteryPercent >= 40 && batteryPercent < 60 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_40_more_no_charging);
            else if (batteryPercent >= 60 && batteryPercent < 80 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_60_more_no_charging);
            else if (batteryPercent >= 80 && batteryPercent < 100 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_80_more_no_charging);
            else if (batteryPercent == 100 && isPlugged == 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_level_100_more_no_charging);
            else if (batteryPercent < 100 && isPlugged != 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_zero_charged);
            else if (batteryPercent == 100 && isPlugged != 0)
                ivBatteryStatus.setImageResource(R.drawable.ic_fully_charged);
        }

        private void setBatteryHealth(int health) {
            if (health == 1)
                tvBatteryHealth.setText("Unknown");
            else if (health == 2)
                tvBatteryHealth.setText("Good");
            else if (health == 3)
                tvBatteryHealth.setText("OverHeated");
            else if (health == 4)
                tvBatteryHealth.setText("Dead");
            else if (health == 5)
                tvBatteryHealth.setText("OverVoltage");
            else
                tvBatteryHealth.setText("Failed");
        }

    }
}