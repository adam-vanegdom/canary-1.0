package com.meetkumarpatel.canary.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.meetkumarpatel.canary.R;

//public class SettingsFragment extends Fragment implements SettingsItemAdapter.OnItemClickListener{
public class SettingsFragment extends Fragment {
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

//        NavHostFragment.findNavController(SettingsFragment.this)
//                .navigate(R.id.action_navigation_settings_to_navigation_bluetooth);
        final TextView textView = view.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        // Configure the recycler view
//        settingsViewModel.setItems(getResources().getStringArray(R.array.settings_items));
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_settings_items);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration((view.getContext()), DividerItemDecoration.VERTICAL));
//        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
//        if (animator instanceof SimpleItemAnimator) {
//            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
//        }
//        final SettingsItemAdapter adapter =new SettingsItemAdapter(settingsViewModel.getItems());
//        adapter.setOnItemClickListener(this);
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    public void onItemClick(@NonNull String item) {
//        if (item == "Bluetooth") {
//            NavHostFragment.findNavController(SettingsFragment.this)
//                    .navigate(R.id.action_navigation_settings_to_navigation_bluetooth);
//        }
    }
}