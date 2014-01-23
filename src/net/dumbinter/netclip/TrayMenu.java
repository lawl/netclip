package net.dumbinter.netclip;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

public class TrayMenu implements ItemListener, ActionListener {
	public static void init(boolean shareClip){
	
		if (!SystemTray.isSupported()) {
			System.out.println("System tray no supported. not displaying quicksettings icon");
			return;
		}
		
        try {
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch (Exception e) {
        	System.err.println("Couldn't set system look & feel");
		}
		
		PopupMenu popMenu = new PopupMenu(); 
		
		CheckboxMenuItem mnuRcvonly = new CheckboxMenuItem("Share Clipboard");
		MenuItem mnuClose = new MenuItem("Close");
		
		TrayMenu listeners = new TrayMenu();
		
		mnuRcvonly.addItemListener(listeners);
		mnuClose.addActionListener(listeners);
		
		mnuRcvonly.setState(shareClip);
		
		popMenu.add(mnuRcvonly);
		popMenu.addSeparator();
		popMenu.add(mnuClose);
		
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("netclip.png");

		Image trayimg;
		
		try {
			trayimg = ImageIO.read(stream);
			TrayIcon trayIcon = new TrayIcon(trayimg, "netclip", popMenu);
			trayIcon.setPopupMenu(popMenu);
			SystemTray.getSystemTray().add(trayIcon);
		} catch (AWTException e) {
			System.out.println("Couldn't add tray image");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("Couldn't read try image");
			e.printStackTrace();
			return;
		}
		
	}
	
	public void itemStateChanged(ItemEvent e) {
	    if (e.getStateChange() == ItemEvent.SELECTED) {
	    	NetClipboard.setListenOnly(false);
	    } else {
	    	NetClipboard.setListenOnly(true);
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
