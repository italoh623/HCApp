package me.aflak.heroicuidador.ble;

import android.bluetooth.BluetoothDevice;

public class BLEDevice {

    private BluetoothDevice bluetoothDevice;
    private int rssi;

    public BLEDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }
}
