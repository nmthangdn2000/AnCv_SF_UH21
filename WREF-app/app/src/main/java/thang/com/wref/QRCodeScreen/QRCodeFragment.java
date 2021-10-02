package thang.com.wref.QRCodeScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;

import thang.com.wref.R;

public class QRCodeFragment extends Fragment {
    private View view;
    private RoundedImageView btnQrCode;
    public QRCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_q_r_code, container, false);
        btnQrCode = (RoundedImageView) view.findViewById(R.id.btnQrCode);

        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScannerActivity.class);
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}