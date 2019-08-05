package candreyskakunenko.myapplication.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import candreyskakunenko.myapplication.R;

public class TextFragment extends Fragment {

    private static final String KEY_TITLE = "Content";



    public TextFragment() {
        // Required empty public constructor
    }
    public static TextFragment newInstance(String param){
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String text = getArguments().getString(KEY_TITLE);
        ((TextView)view.findViewById(R.id.text_fragment_view)).setText(text);
    }
}
