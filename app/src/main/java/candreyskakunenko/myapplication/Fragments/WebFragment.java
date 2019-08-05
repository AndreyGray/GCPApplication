package candreyskakunenko.myapplication.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.Objects;

import candreyskakunenko.myapplication.R;


public class WebFragment extends Fragment {
    private static final String KEY_TITLE = "Content";


    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance(String param){
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = Objects.requireNonNull(getArguments()).getString(KEY_TITLE);
        WebView webView = view.findViewById(R.id.web_fragment_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
