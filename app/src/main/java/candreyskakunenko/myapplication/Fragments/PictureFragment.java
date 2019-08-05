package candreyskakunenko.myapplication.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import candreyskakunenko.myapplication.R;


public class PictureFragment extends Fragment {

    private static final String KEY_TITLE = "Content";


    public PictureFragment() {
        // Required empty public constructor
    }

    public static PictureFragment newInstance(String param){
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = ((ImageView)view.findViewById(R.id.image_fragment_view));
        String imageUrl = getArguments().getString(KEY_TITLE);

        Picasso.get()
                .load(imageUrl)
                //.centerCrop()
                .into(imageView);
    }
}
