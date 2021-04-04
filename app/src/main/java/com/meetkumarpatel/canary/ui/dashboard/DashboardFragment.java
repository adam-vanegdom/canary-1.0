package com.meetkumarpatel.canary.ui.dashboard;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.meetkumarpatel.canary.R;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private final int REQUEST_ENABLE_BLUETOOTH = 1;

    private final int NOT_CONNECTED = 0;
    private final int SEARCHING = 1;
    private final int FOUND = 2;
    private final int CONNECTED = 3;
    private final int DISCOVERING = 4;
    private final int COMMUNICATING = 5;
    private final int TOGGLE_DOOR = 6;
    private final int DISCONNECTING = 7;
    private final int INTERROGATE = 8;
    private final int GET_READINGS = 9;

    private BluetoothAdapter bluetoothAdapter;

//    Queue<BLEQueueItem> taskQ = new LinkedList<BLEQueueItem>();
    BLERemoteDevice device = new BLERemoteDevice(GET_READINGS);

    private TextView bleStatusTextView;
    private Button getReadingsBtn;
//    private Button connectBtn;
    private String tag = "dashboard";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        final TextView textView = view.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // MAKE DO CODE STARTS HERE
        // initialize bluetooth adapter
        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // ensure bluetooth is available on the device and it is enabled. If not, display a dialog requesting user permission to enable Bluetooth
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Log.i(tag, "No BLE");

            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
        }

        // setup all the text views
        bleStatusTextView = (TextView) view.findViewById(R.id.bleStatusTextView);
        final TextView pm1TextView = (TextView) view.findViewById(R.id.pm1TextView);
        final TextView pm25TextView = (TextView) view.findViewById(R.id.pm25TextView);
        final TextView pm10TextView = (TextView) view.findViewById(R.id.pm10TextView);
        final TextView vocTextView = (TextView) view.findViewById(R.id.vocTextView);
        final TextView gasTextView = (TextView) view.findViewById(R.id.gasTextView);
        final TextView temperatureTextView = (TextView) view.findViewById(R.id.tempTextView);
        final TextView humidityTextView = (TextView) view.findViewById(R.id.humidityTextView);
        final TextView pmAqiTextView = (TextView) view.findViewById(R.id.pmAqiTextView);
        final TextView gasAqiTextView = (TextView) view.findViewById(R.id.gasAqiTextView);

        dashboardViewModel.getPm1Data().observe(getViewLifecycleOwner(), pm1TextView::setText);

        dashboardViewModel.getPm25Data().observe(getViewLifecycleOwner(), pm25TextView::setText);

        dashboardViewModel.getPm10Data().observe(getViewLifecycleOwner(), pm10TextView::setText);

        dashboardViewModel.getVocData().observe(getViewLifecycleOwner(), vocTextView::setText);

        dashboardViewModel.getGasData().observe(getViewLifecycleOwner(), gasTextView::setText);

        dashboardViewModel.getTemperatureData().observe(getViewLifecycleOwner(), temperatureTextView::setText);

        dashboardViewModel.getHumidityData().observe(getViewLifecycleOwner(), humidityTextView::setText);

        dashboardViewModel.getPmAqiData().observe(getViewLifecycleOwner(), pmAqiTextView::setText);

        dashboardViewModel.getGasAqiData().observe(getViewLifecycleOwner(), gasAqiTextView::setText);

//        connectBtn = (Button) view.findViewById(R.id.connectBtn);
        getReadingsBtn = (Button) view.findViewById(R.id.getReadingsBtn);

//        connectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(tag, "Initialize Scan");
//                handler.sendEmptyMessage(SEARCHING);
//                bluetoothAdapter.getBluetoothLeScanner().startScan(new BLEFoundDevice(INTERROGATE));
//            }
//        });

        getReadingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Initialize Scan");
                bluetoothAdapter.getBluetoothLeScanner().startScan(new BLEFoundDevice(GET_READINGS));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

//        device.taskQ.add(new BLEQueueItem(BLEQueueItem.DISCONNECT, new UUID(0, 0), "Disconnect", null));
//        device.doNextThing(device.gatt);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case NOT_CONNECTED:
                    bleStatusTextView.setText("Not Connected");
                    break;
                case SEARCHING:
                    bleStatusTextView.setText("Searching");
                    break;
                case FOUND:
                    bleStatusTextView.setText("Found");
                    break;
                case CONNECTED:
                    bleStatusTextView.setText("Connected");
                    break;
                case DISCOVERING:
                    bleStatusTextView.setText("Discovering");
                    break;
                case COMMUNICATING:
                    bleStatusTextView.setText("Communicating");
                    break;
                case TOGGLE_DOOR:
                    bleStatusTextView.setText("Communicating");
                    break;
                case DISCONNECTING:
                    bleStatusTextView.setText("Disconnecting");
                    break;
            }

            return true;
        }
    });

    final class BLERemoteDevice extends BluetoothGattCallback {
        private final String tag = "BLE_DEVICE";
//        UUID serviceWeWant = new UUID(0x0000FA0100001000L, 0x800000805f9b34fbL);
        UUID serviceWeWant = new UUID(0x000015231212EFDEL, 0x1523785FEABCD123L);
        UUID toggleButtonUUID = new UUID(0x0000210200001000L, 0x800000805f9b34fbL);

        UUID pm1UUID = new UUID(0x000015251212EFDEL, 0x1523785FEABCD123L);
        UUID pm25UUID = new UUID(0x000015261212EFDEL, 0x1523785FEABCD123L);
        UUID pm10UUID = new UUID(0x000015271212EFDEL, 0x1523785FEABCD123L);
        UUID vocUUID = new UUID(0x000015281212EFDEL, 0x1523785FEABCD123L);
        UUID gasUUID = new UUID(0x000015291212EFDEL, 0x1523785FEABCD123L);
        UUID temperatureUUID = new UUID(0x0000152A1212EFDEL, 0x1523785FEABCD123L);
        UUID humidityUUID = new UUID(0x0000152B1212EFDEL, 0x1523785FEABCD123L);
        UUID pmAqiUUID = new UUID(0x0000152C1212EFDEL, 0x1523785FEABCD123L);
        UUID gasAqiUUID = new UUID(0x0000152D1212EFDEL, 0x1523785FEABCD123L);
        BluetoothGatt gatt = null;

        byte toggleDoorValue[] = {0x55};
        Queue<BLEQueueItem> taskQ = new LinkedList<BLEQueueItem>();

        private int mode = INTERROGATE;

        public BLERemoteDevice(int mode) {
            this.mode = mode;
        }

        public void doNextThing(BluetoothGatt gatt) {
            Log.i(tag, "About to try to doNextThing");

            try {
                BLEQueueItem thisTask = taskQ.poll();

                if (thisTask != null) {
                    Log.i(tag, "processing " + thisTask.toString());

                    switch (thisTask.getAction()) {
                        case BLEQueueItem.READ_CHARACTERISTIC:
                            gatt.readCharacteristic((BluetoothGattCharacteristic) thisTask.getObject());
                            break;
                        case BLEQueueItem.WRITE_CHARACTERISTIC:
                            Log.i(tag, "Write out this Characteristic");
                            handler.sendEmptyMessage(TOGGLE_DOOR);

                            BluetoothGattCharacteristic c = (BluetoothGattCharacteristic) thisTask.getObject();

                            Log.i(tag, "Value to be written is [" + c.getStringValue(0) + "]");

                            gatt.writeCharacteristic(c);
                            break;
                        case BLEQueueItem.READ_DESCRIPTOR:
                            gatt.readDescriptor((BluetoothGattDescriptor) thisTask.getObject());
                            break;
                        case BLEQueueItem.DISCONNECT:
                            handler.sendEmptyMessage(DISCONNECTING);
                            gatt.disconnect();
                            break;
                    }
                }
                else {
                    Log.i(tag, "no more tasks, peace out");
                }
            } catch (Exception e) {
                Log.i(tag, "Error in doNextThing " + e.getMessage());
            }
        }

        @Override
        public void onConnectionStateChange (BluetoothGatt gatt, int status, int newState) {
            if (this.gatt == null) {
                this.gatt = gatt;
            }

            Log.i(tag,"onConnectionStatChange [" + status + "][" + newState  + "]");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    Log.i(tag,"Connected to [" + gatt.toString() + "]");
                    handler.sendEmptyMessage(DISCOVERING);
                    gatt.discoverServices();
                } else if (status == BluetoothGatt.STATE_DISCONNECTED) {
                    handler.sendEmptyMessage((NOT_CONNECTED));
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i(tag,"OnServiceDiscovered ["+ status + "] " + gatt.toString());
            if (mode == INTERROGATE) {
                List<BluetoothGattService> services = gatt.getServices();
                for (int i = 0; i < services.size(); i++) {
                    Log.i(tag, "service [" + i + "] is [" + services.get(i).getUuid().toString() + "]");
                    if (serviceWeWant.equals(services.get(i).getUuid())) {
                        Log.i(tag, "************COOL, found it!!!");
                    }
                    UUID serviceUUID = services.get(i).getUuid();
                    List<BluetoothGattCharacteristic> schars = services.get(i).getCharacteristics();
                    for (int j = 0; j < schars.size(); j++) {
                        Log.i(tag, "characteristic [" + j + "] [" + schars.get(j).getUuid() + "] properties [" + schars.get(j).getProperties() + "]");
                        if ((schars.get(j).getProperties() & 2) == 2) {
                            taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, schars.get(j).getUuid(), "Read Characteristic of Available Service", schars.get(j)));
                        } else {
                            Log.i(tag, "This Characteristic cannot be Read");
                        }
                        List<BluetoothGattDescriptor> scdesc = schars.get(j).getDescriptors();
                        for (int k = 0; k < scdesc.size(); k++) {
                            Log.i(tag, "Descriptor [" + k + "] [" + scdesc.get(k).toString() + "]");
                            Log.i(tag, "Descriptor UUID [" + scdesc.get(k).getUuid() + "]");
                            Log.i(tag, "Descriptor Permissions [" + scdesc.get(k).getPermissions() + "]");
                            //Log.i(tag,"Attempting to read this Descriptor");
                            taskQ.add(new BLEQueueItem(BLEQueueItem.READ_DESCRIPTOR, scdesc.get(k).getUuid(), "Read Descriptor of Characteristic", scdesc.get(k)));
                        }
                    }
                }
            }

            if (mode == TOGGLE_DOOR) {
                BluetoothGattService garageDoorOpener = gatt.getService(serviceWeWant);
                if (garageDoorOpener != null) {
                    Log.i(tag, "Got it, woo hoo!!!");
                    BluetoothGattCharacteristic toggleDoor = garageDoorOpener.getCharacteristic(toggleButtonUUID);
                    if (toggleDoor != null) {
                        Log.i(tag, "Got the button");
                        Log.i(tag, "value is [" + toggleDoor.getStringValue(0) + "]");
                        toggleDoor.setValue(toggleDoorValue);
                        Log.i(tag, "value is [" + toggleDoor.getStringValue(0) + "]");
                        taskQ.add(new BLEQueueItem(BLEQueueItem.WRITE_CHARACTERISTIC, toggleDoor.getUuid(), "Write Characteristic to Toggle Door", toggleDoor));
                    } else {
                        Log.i(tag, "No button");
                    }
                } else {
                    Log.i(tag, "No Service");
                }
            }

            if (mode == GET_READINGS) {
                BluetoothGattService myCanary = gatt.getService(serviceWeWant);
                if (myCanary != null) {
                    Log.i(tag, "Got it, woo hoo!!!");
                    BluetoothGattCharacteristic pm1Char = myCanary.getCharacteristic(pm1UUID);
                    BluetoothGattCharacteristic pm25Char = myCanary.getCharacteristic(pm25UUID);
                    BluetoothGattCharacteristic pm10Char = myCanary.getCharacteristic(pm10UUID);
                    BluetoothGattCharacteristic vocChar = myCanary.getCharacteristic(vocUUID);
                    BluetoothGattCharacteristic gasChar = myCanary.getCharacteristic(gasUUID);
                    BluetoothGattCharacteristic temperatureChar = myCanary.getCharacteristic(temperatureUUID);
                    BluetoothGattCharacteristic humidityChar = myCanary.getCharacteristic(humidityUUID);
                    BluetoothGattCharacteristic pmAqiChar = myCanary.getCharacteristic(pmAqiUUID);
                    BluetoothGattCharacteristic gasAqiChar = myCanary.getCharacteristic(gasAqiUUID);
                    if (pm1Char != null && pm25Char != null && pm10Char != null &&
                            vocChar != null && gasChar != null && temperatureChar != null &&
                            humidityChar != null && pmAqiChar != null && gasAqiChar != null) {
                        Log.i(tag, "Got the reading requests");

                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, pm1Char.getUuid(), "Read Characteristic to pm1Char", pm1Char));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, pm25Char.getUuid(), "Read Characteristic to pm25Char", pm25Char));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, pm10Char.getUuid(), "Read Characteristic to pm10Char", pm10Char));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, vocChar.getUuid(), "Read Characteristic to vocChar", vocChar));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, gasChar.getUuid(), "Read Characteristic to gasChar", gasChar));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, temperatureChar.getUuid(), "Read Characteristic to temperatureChar", temperatureChar));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, humidityChar.getUuid(), "Read Characteristic to humidityChar", humidityChar));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, pmAqiChar.getUuid(), "Read Characteristic to pmAqiChar", pmAqiChar));
                        taskQ.add(new BLEQueueItem(BLEQueueItem.READ_CHARACTERISTIC, gasAqiChar.getUuid(), "Read Characteristic to gasAqiChar", gasAqiChar));
                    } else {
                        Log.i(tag, "At least one of the characteristic is not supported");
                    }
                } else {
                    Log.i(tag, "Canary does not exist");
                }
            }

            Log.i(tag, "OK, let's go^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            taskQ.add(new BLEQueueItem(BLEQueueItem.DISCONNECT, new UUID(0, 0), "Disconnect", null));
            handler.sendEmptyMessage(COMMUNICATING);
            doNextThing(gatt);
        }

        @Override
        public void onCharacteristicWrite (BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i(tag,"characteristic written [" + status + "]");
            if (characteristic.getUuid().equals(toggleButtonUUID)) {
                Log.i(tag,"value is [" + characteristic.getStringValue(0) + "]");
                if (characteristic.getStringValue(0).equals(("U"))) {
                    Log.i(tag,"We're done here!");
                }
            }
            doNextThing(gatt);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.i(tag,"onCharacteristicChanged " + characteristic.getUuid());
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            int tempInt = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 0);
            String tempStr = String.valueOf(tempInt);
            Log.i(tag,"characteristic read [" + characteristic.getUuid() + "] [" + tempInt + "]");

            if (characteristic.getUuid().equals(pm1UUID)) {
                dashboardViewModel.setPm1Data(tempStr);
            }
            else if (characteristic.getUuid().equals(pm25UUID)) {
                dashboardViewModel.setPm25Data(tempStr);
            }
            else if (characteristic.getUuid().equals(pm10UUID)) {
                dashboardViewModel.setPm10Data(tempStr);
            }
            else if (characteristic.getUuid().equals(vocUUID)) {
                dashboardViewModel.setVocData(tempStr);
            }
            else if (characteristic.getUuid().equals(gasUUID)) {
                dashboardViewModel.setGasData(tempStr);
            }
            else if (characteristic.getUuid().equals(temperatureUUID)) {
                dashboardViewModel.setTemperatureData(tempStr);
            }
            else if (characteristic.getUuid().equals(humidityUUID)) {
                dashboardViewModel.setHumidityData(tempStr);
            }
            else if (characteristic.getUuid().equals(pmAqiUUID)) {
                dashboardViewModel.setPmAqiData(tempStr);
            }
            else if (characteristic.getUuid().equals(gasAqiUUID)) {
                dashboardViewModel.setGasAqiData(tempStr);
            }

            doNextThing(gatt);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            try {
                Log.i(tag,"onDescriptorRead status is [" + status + "]");
                Log.i(tag, "descriptor read [" + descriptor.getCharacteristic().getUuid() + "]");
                Log.i(tag, "descriptor value is [" + new String(descriptor.getValue(), StandardCharsets.UTF_8) + "]");
                doNextThing(gatt);
            } catch (Exception e) {
                Log.e(tag,"Error reading descriptor " + e.getStackTrace());
                doNextThing(gatt);
            }
        }

        public void setMode(int mode) {
            this.mode = mode;
        }
    }

    private class BLEFoundDevice extends ScanCallback {
        private final String tag = "BLE_DEVICE";
        private int mode = INTERROGATE;
        public BLEFoundDevice(int mode) {
            this.mode = mode;
        }

        @Override
        public void onScanResult(int callBackType, ScanResult result) {
            Log.i(tag, "Found a device ==> " + result.toString());

            ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord != null) {
                if (scanRecord.getDeviceName() != null) {
                    if (scanRecord.getDeviceName().equals("Canary")) {
                        bluetoothAdapter.getBluetoothLeScanner().stopScan(this);

                        handler.sendEmptyMessage(FOUND);
                        Log.i(tag, "Found our Canary!");

                        BluetoothDevice remoteDevice = result.getDevice();
                        if (remoteDevice != null) {
                            String nameOfDevice = remoteDevice.getName();
                            if (nameOfDevice != null) {
                                Log.i(tag, "device is [" + nameOfDevice + "]");
                            }
                        }

                        Log.i(tag, "Advertise Flags [" + scanRecord.getAdvertiseFlags() + "]");

                        List<ParcelUuid> solicitationInfo = scanRecord.getServiceUuids();
                        for (int i = 0; i < solicitationInfo.size(); i++) {
                            ParcelUuid thisone = solicitationInfo.get(i);
                            Log.i(tag, "soliciationInfo [" + i + "] uuid [" + thisone.getUuid() + "]");
                        }

                        ParcelUuid[] services = remoteDevice.getUuids();
                        if (services != null) {
                            Log.i(tag, "length of services is [" + services.length + "]");
                        }

                        // attempt to connect here
                        device.setMode(mode);
                        remoteDevice.connectGatt(getActivity(), true, device);
                        Log.i(tag, "after connect GATT");
                    }
                    else {
                        Log.i(tag, "Not for us [" + scanRecord.getDeviceName() + "]");
                    }
                }
            }
            else {
                Log.i(tag, "Null ScanRecord??");
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e(tag, "Error Scanning [" + errorCode + "]");
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            Log.i(tag, "onBatchScanResults " + results.size());

            for (int i = 0; i < results.size(); i++) {
                Log.i(tag, "Result [" + i + "]" + results.get(i).toString());
            }
        }
    }
}