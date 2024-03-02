package org.example;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();

        // Имя компьютера
        String machineName = os.getNetworkParams().getHostName();
        System.out.println("Machine Name: " + machineName);

        // Получаем информацию о типе системы
        String systemType = os.getBitness() + "-разрядная операционная система, процессор " + (is64Bit() ? "x64" : "x32");
        System.out.println("System Type: " + systemType);

        // Информация о выпуске операционной системы
        Properties props = System.getProperties();
        String osVersion = props.getProperty("os.name") + " " + props.getProperty("os.version");
        System.out.println("Operating System Version: " + osVersion);

        // Получаем информацию о процессоре
        CentralProcessor processor = hardware.getProcessor();
        System.out.println("Processor: " + processor.getProcessorIdentifier().getName());
        System.out.println("Processor Version: " + processor.getProcessorIdentifier().getStepping());

        // Получаем информацию о памяти
        GlobalMemory memory = hardware.getMemory();
        System.out.println("Memory: " + FormatUtil.formatBytes(memory.getTotal()));

        // Получаем информацию о дисках
        for (HWDiskStore disk : hardware.getDiskStores()) {
            System.out.println("Disk: " + disk.getName() + ", Size: " + FormatUtil.formatBytesDecimal(disk.getSize()));
        }

        // Получаем информацию о сетевых интерфейсах
        for (NetworkIF net : hardware.getNetworkIFs()) {
            System.out.println("Network Interface: " + net.getName() + ", Speed: " + FormatUtil.formatValue(net.getSpeed(), "bps"));
        }
    }

    private static boolean is64Bit() {
        return System.getProperty("os.arch").contains("64");
    }
}