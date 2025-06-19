package testgroup.plantsvszombies.multiPlayer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetRealIP {
    public static InetAddress getIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                // Skip loopback interfaces and interfaces that are down
                if (ni.isLoopback() || !ni.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    System.out.println(addr);
                    // Only consider IPv4 addresses that are not loopback or link-local
                    if (addr instanceof java.net.Inet4Address && !addr.isLoopbackAddress() && !addr.isLinkLocalAddress()) {
                        System.out.println("Found network IP address: " + addr.getHostAddress());
                        // This is likely the IP you want for local network multiplayer
                        // You might want to return the first one found or list all
                        return addr; // Exit after finding the first suitable IP
                    }
                }
            }
            System.out.println("No non-loopback IPv4 address found.");
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}