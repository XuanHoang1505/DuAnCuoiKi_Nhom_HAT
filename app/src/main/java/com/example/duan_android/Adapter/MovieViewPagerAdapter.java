package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_android.Fragment.ComingSoonFragment;
import com.example.duan_android.Fragment.CommentFragment;
import com.example.duan_android.Fragment.NewsFragment;
import com.example.duan_android.Fragment.ShowingFragment;
import com.example.duan_android.Fragment.ViewMoreComingSoonFragment;
import com.example.duan_android.Fragment.ViewMoreShowingFragment;

public class MovieViewPagerAdapter extends FragmentPagerAdapter {
    private boolean isForViewMore;
    private boolean isForMovie; // New flag for the new TabLayout

    public MovieViewPagerAdapter(@NonNull FragmentManager fm, int behavior, boolean isForViewMore, boolean isForMovie) {
        super(fm, behavior);
        this.isForViewMore = isForViewMore;
        this.isForMovie = isForMovie;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (isForMovie) {
            // Fragments for new TabLayout (Comment, News, Characters)
            switch (position) {
                case 0:
                    return new CommentFragment();
                case 1:
                    return new NewsFragment();
                default:
                    return new CommentFragment();
            }
        } else if (isForViewMore) {
            // Fragments for ViewMoreActivity
            switch (position) {
                case 0:
                    return new ViewMoreShowingFragment();
                case 1:
                    return new ViewMoreComingSoonFragment();
                default:
                    return new ViewMoreShowingFragment();
            }
        } else {
            // Fragments for HomeFragment
            switch (position) {
                case 0:
                    return new ShowingFragment();
                case 1:
                    return new ComingSoonFragment();
                default:
                    return new ShowingFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (isForMovie) {
            switch (position) {
                case 0:
                    return "Bình luận";
                case 1:
                    return "Blog điện ảnh";

            }
        } else {
            switch (position) {
                case 0:
                    return "Đang chiếu";
                case 1:
                    return "Sắp chiếu";
            }
        }
        return null;
    }
}
