package com.example.asus.songrecognition;


import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.songrecognition.Presenter.IdentifyPresenter;
import com.example.asus.songrecognition.Presenter.IdentifyView;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdentifyFragment extends Fragment implements IdentifyView {

    private TextView tvResult;
    private TextView tvNotification;
    private ImageButton btnRecord;
    private ImageView micImage;
    private PulsatorLayout pulsatorLayout;
    private Animation animScale;
    IdentifyPresenter identifyPresenter;

    private static final int REQUEST_PERMISSION_CODE = 1;

    public IdentifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identify, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        identifyPresenter = new IdentifyPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pulsatorLayout = getView().findViewById(R.id.pulsator);
        btnRecord = getView().findViewById(R.id.btn_record);
        micImage = getView().findViewById(R.id.imgV_mic);
        tvResult = getView().findViewById(R.id.tv_result);
        tvNotification = getView().findViewById(R.id.tvNotification);
        animScale = AnimationUtils.loadAnimation(getActivity(),R.anim.scale);

        identifyPresenter.checkInternetConnection();
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                micImage.startAnimation(animScale);
                btnRecord.startAnimation(animScale);
                setTVResultText("");
                tvNotification.setText("");
                identifyPresenter.start();
            }
        });
    }

    @Override
    public void setTVResultText(String text) {
        tvResult.setText(text);
    }

    @Override
    public void startPulsatorLayout() {
        pulsatorLayout.start();
    }

    @Override
    public void stopPulsatorLayout() {
        pulsatorLayout.stop();
    }

    @Override
    public boolean checkPermission() {
        int permissionWrite = getPermission(WRITE_EXTERNAL_STORAGE);
        int permissionRecord = getPermission(RECORD_AUDIO);
        return permissionWrite == PackageManager.PERMISSION_GRANTED &&
                permissionRecord == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void setTvNotificationText(String text) {
       tvNotification.setText(text);
    }

    public int getPermission(String permission) {
        return ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                permission);
    }

    @Override
    public Context getAppContext(){
        return getActivity().getApplicationContext();
    }
}
