/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.gsitm.theme.bioauth;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsitm.theme.R;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * A dialog which uses fingerprint APIs to authenticate the user, and falls back to password
 * authentication if fingerprint is not available.
 */
public class FingerprintAuthenticationDialogFragment extends DialogFragment
        implements FingerprintUiHelper.Callback {

    private Button mCancelButton;
    private View mFingerprintContent;

    private FingerprintManagerCompat.CryptoObject mCryptoObject;
    private FingerprintUiHelper mFingerprintUiHelper;



    private SecretAuthorize secretAuthorize;

    private Animation vibrateAnim;


    public FingerprintAuthenticationDialogFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);

        if (Build.VERSION.SDK_INT > LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
        } else {

        }
        vibrateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.vibrate_anim);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.sign_in));
        View v = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
        mCancelButton = (Button) v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        mFingerprintContent = v.findViewById(R.id.fingerprint_container);
        mFingerprintUiHelper = new FingerprintUiHelper(getActivity().getApplicationContext(),
                (ImageView) v.findViewById(R.id.fingerprint_icon),
                (TextView) v.findViewById(R.id.fingerprint_status), this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mFingerprintUiHelper.startListening(mCryptoObject);

    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintUiHelper.stopListening();
    }


    @Override
    public void onAuthenticated() {
        // 인증 성공시
        dismiss();
        secretAuthorize.success();
    }

    @Override
    public void onError() {
        //5회이상 틀렸을때.
        this.dismiss();
    }


    public void setCallback(SecretAuthorize secretAuthorize){
        this.secretAuthorize = secretAuthorize;
    }

    /**
     * 지문 인증 성공, 실패시 -> 프래그먼트단에서 구현 할수 있게.
     */
    public interface SecretAuthorize {
        void success();
        void fail();
    }
}
