package com.meetkumarpatel.canary.ui.settings.ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.ui.settings.ble.adapter.DevicesAdapter;
import com.meetkumarpatel.canary.ui.settings.ble.adapter.DiscoveredBluetoothDevice;
import com.meetkumarpatel.canary.ui.settings.ble.utils.Utils;
import com.meetkumarpatel.canary.ui.settings.ble.viewmodels.ScannerStateLiveData;
import com.meetkumarpatel.canary.ui.settings.ble.viewmodels.ScannerViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class BluetoothFragment extends Fragment implements DevicesAdapter.OnItemClickListener{
    private static final int REQUEST_ACCESS_FINE_LOCATION = 1022; // random number

    private ScannerViewModel scannerViewModel;

    @BindView(R.id.no_devices) View emptyView;
    @BindView(R.id.no_location_permission) View noLocationPermissionView;
    @BindView(R.id.action_grant_location_permission) Button grantPermissionButton;
    @BindView(R.id.action_permission_settings) Button permissionSettingsButton;
    @BindView(R.id.no_location) View noLocationView;
    @BindView(R.id.bluetooth_off) View noBluetoothView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create view model containing utility methods for scanning
        scannerViewModel = new ViewModelProvider(this).get(ScannerViewModel.class);
        scannerViewModel.getScannerState().observe(getActivity(), this::startScan);

        // Configure the recycler view
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view_ble_devices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        final RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        final DevicesAdapter adapter = new DevicesAdapter(this, scannerViewModel.getDevices());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        clear();
//    }

    @Override
    public void onStop() {
        super.onStop();
        stopScan();
    }

//    @Override
//    public boolean onCreateOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.filter, menu);
//        menu.findItem(R.id.filter_uuid).setChecked(scannerViewModel.isUuidFilterEnabled());
//        menu.findItem(R.id.filter_nearby).setChecked(scannerViewModel.isNearbyFilterEnabled());
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.filter_uuid:
//                item.setChecked(!item.isChecked());
//                scannerViewModel.filterByUuid(item.isChecked());
//                return true;
//            case R.id.filter_nearby:
//                item.setChecked(!item.isChecked());
//                scannerViewModel.filterByDistance(item.isChecked());
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onItemClick(@NonNull final DiscoveredBluetoothDevice device) {
//        final Intent controlBlinkIntent = new Intent(getActivity(), BlinkyActivity.class);
//        controlBlinkIntent.putExtra(BlinkyActivity.EXTRA_DEVICE, device);
//        startActivity(controlBlinkIntent);
        NavHostFragment.findNavController(BluetoothFragment.this)
                .navigate(R.id.action_navigation_bluetooth_to_navigation_dashboard);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            scannerViewModel.refresh();
        }
    }

    @OnClick(R.id.action_enable_location)
    public void onEnableLocationClicked() {
        final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @OnClick(R.id.action_enable_bluetooth)
    public void onEnableBluetoothClicked() {
        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivity(enableIntent);
    }

    @OnClick(R.id.action_grant_location_permission)
    public void onGrantLocationPermissionClicked() {
        Utils.markLocationPermissionRequested(getActivity());
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_ACCESS_FINE_LOCATION);
    }

    @OnClick(R.id.action_permission_settings)
    public void onPermissionSettingsClicked() {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivity(intent);
    }

    /**
     * Start scanning for Bluetooth devices or displays a message based on the scanner state.
     */
    private void startScan(final ScannerStateLiveData state) {
        // First, check the Location permission. This is required on Marshmallow onwards in order
        // to scan for Bluetooth LE devices.
//        if (Utils.isLocationPermissionsGranted(getActivity())) {
//            noLocationPermissionView.setVisibility(View.GONE);
//
//            // Bluetooth must be enabled.
//            if (state.isBluetoothEnabled()) {
//                noBluetoothView.setVisibility(View.GONE);
//
//                // We are now OK to start scanning.
//                scannerViewModel.startScan();
////                scanningView.setVisibility(View.VISIBLE);
//
//                if (!state.hasRecords()) {
//                    emptyView.setVisibility(View.VISIBLE);
//
//                    if (!Utils.isLocationRequired(getActivity()) || Utils.isLocationEnabled(getActivity())) {
//                        noLocationView.setVisibility(View.INVISIBLE);
//                    } else {
//                        noLocationView.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    emptyView.setVisibility(View.GONE);
//                }
//            } else {
//                noBluetoothView.setVisibility(View.VISIBLE);
////                scanningView.setVisibility(View.INVISIBLE);
//                emptyView.setVisibility(View.GONE);
//                clear();
//            }
//        } else {
//            noLocationPermissionView.setVisibility(View.VISIBLE);
//            noBluetoothView.setVisibility(View.GONE);
////            scanningView.setVisibility(View.INVISIBLE);
//            emptyView.setVisibility(View.GONE);
//
//            final boolean deniedForever = Utils.isLocationPermissionDeniedForever(getActivity());
//            grantPermissionButton.setVisibility(deniedForever ? View.GONE : View.VISIBLE);
//            permissionSettingsButton.setVisibility(deniedForever ? View.VISIBLE : View.GONE);
//        }
    }

    /**
     * stop scanning for bluetooth devices.
     */
    private void stopScan() {
        scannerViewModel.stopScan();
    }

    /**
     * Clears the list of devices, which will notify the observer.
     */
    private void clear() {
        scannerViewModel.getDevices().clear();
        scannerViewModel.getScannerState().clearRecords();
    }
}