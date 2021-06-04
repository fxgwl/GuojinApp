package com.axehome.www.guojinapp.ui.activitys.viewpager.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.ui.activitys.BandPhoneActivity;
import com.axehome.www.guojinapp.utils.MyUtils;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPager3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPager3Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.b_submit)
    Button bSubmit;
    private Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ViewPager3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPager2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPager3Fragment newInstance(String param1, String param2) {
        ViewPager3Fragment fragment = new ViewPager3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_pager3, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @OnClick(R.id.b_submit)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.b_submit:
                User user= MyUtils.getUser();
                if(MyUtils.getUser()==null){
                    startActivity(new Intent(getContext(), MainActivity.class)
                            .putExtra("from", "login"));
                }else{
                    if(MyUtils.getUser().getPhone()!=null && !MyUtils.getUser().getPhone().equals("")){
                        startActivity(new Intent(getContext(), MainActivity.class)
                                .putExtra("from", "login"));
                    }else{
                        startActivity(new Intent(getContext(), BandPhoneActivity.class));
                    }
                }

                getActivity().finish();
                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                getActivity().overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
