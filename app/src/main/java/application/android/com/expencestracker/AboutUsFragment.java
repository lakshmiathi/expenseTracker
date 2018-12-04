package application.android.com.expencestracker;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    TextView textView;




    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ExpandableListView expandableListView =(ExpandableListView)view.findViewById(R.id.Explistview);
        textView=(TextView)view.findViewById(R.id.textView3);
        textView.setText("Thanks for using ExTrack");
        textView.setTypeface(null,Typeface.BOLD);
        textView.setTextSize(20f);
        textView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.sequential_animation));
        expandableListView.setAdapter(new ExpandabelListAdoptor());

        return view;
    }


    public class ExpandabelListAdoptor extends BaseExpandableListAdapter {

        private String[] aboutUs={"Introduction","Value Proposition","Contact Us","Version","Fork Us On GitHub"};
        private String[][] child={
                {
                        "ExTrack is one stop Solution for all your expense management needs." +
                        "ExTrack aims to provide a hassle free and enjoyable" +
                        "expense management experience for all users across world." +
                        "ExTrack is making a conscious effort to bring the power of Expense Management" +
                        "to users and make track of all expenses in day to day life" +
                                "User can set Limit for his expenses and ExTrack sends you notification message " +
                                "when the limit is crossed "
                   },
                {"ExTrack's value proposition revolves around giving customer the power and easy of tracking expenses Offline" +
                        ".To make tracking easier for you,a dedicated customer connect team is on standby " +
                        "to answer your queries .The next versions of the ExTrack is coming soon with lot of" +
                        " new exciting features"},
                {"For any queries or issues please feel free to contact us at ExtrackApp@gmail.com" +
                        "Telephone: 0801255555"},
                {"1.0"},
                {"https://github.com/Seif-Abedsslem/ExpenseTracker"}

        };

        @Override
        public int getGroupCount() {
            return aboutUs.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return aboutUs[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView= new TextView(AboutUsFragment.this.getActivity());
            textView.setText(getGroup(groupPosition).toString());
            textView.setTypeface(null,Typeface.BOLD_ITALIC);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18f);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
           TextView textView = new TextView(AboutUsFragment.this.getActivity());
           textView.setText(getChild(groupPosition,childPosition).toString());
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}