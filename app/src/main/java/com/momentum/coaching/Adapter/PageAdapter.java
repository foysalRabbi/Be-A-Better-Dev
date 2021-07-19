package com.momentum.coaching.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.momentum.coaching.Fragment.Department;
import com.momentum.coaching.Fragment.Formulas;
import com.momentum.coaching.Fragment.NonDepartment;
import com.momentum.coaching.Fragment.QuizFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Department tab1 = new Department();
                return tab1;
            case 1:
                NonDepartment tab2 = new NonDepartment();
                return tab2;
            case 2:
                Formulas formulas = new Formulas();
                return formulas;
            case 3:
                QuizFragment quiz = new QuizFragment();
                return quiz;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
