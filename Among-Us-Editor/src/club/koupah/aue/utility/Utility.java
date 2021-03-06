package club.koupah.aue.utility;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;

import club.koupah.AUEditorMain;
import club.koupah.aue.Editor;

public class Utility {

	
	public static void runUpdateCheck(final JButton button) {

		if (button != null)
			button.setEnabled(false);

		/*
		 * VERSION CHECK FOR PEOPLE WHO DOWNLOAD STRAIGHT FROM A YOUTUBE VIDEO ETC Feel
		 * free to remove this if you're compiling yourself!
		 */
		Thread updateCheck = new Thread() {
			public void run() {
				
				double version = Editor.version;
				
				// Made these booleans private, they're only used here
				boolean preRelease = false;
				boolean outdated = false;
				try {
					URLConnection connection = new URL(
							"https://raw.githubusercontent.com/Koupah/Among-Us-Editor/master/version").openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

					// The string value of the latest version
					String latestVersionString = in.readLine();
					// The double value of the latest version
					double latestVersion = Double.parseDouble(latestVersionString);

					// Check if the latest version is greater than our version
					outdated = latestVersion > version;

					// If the current version is ahead of the 'latest' then we're on a prerelease
					preRelease = latestVersion < version;

					if (outdated) {
						String info = "";
						String read;
						while ((read = in.readLine()) != null) {
							info += read + "\n";
						}

						String message = "You're on version " + version + " and " + latestVersionString
								+ " is the latest!"
						// If there is info (not just like a space or whatever) then show the message
								+ (info.length() > 2 ? "\n\nVersion " + latestVersionString + ":\n" + info : "");

						// Create a new pop up with the message and allows for opening the new version
						// in browser
						PopUp.downloadPopUp(message, latestVersionString);
					} else {
						if (button != null) {
							if (preRelease) {
								new PopUp("You are using a PRE release version!\nThe latest version is " + latestVersionString
										+ " but you're on " + version, false);
							} else {
								new PopUp("No updates available!\n\nYou are using the latest version,\nVersion: "
										+ latestVersionString, false);
							}
						}
					}
					in.close();
				} catch (Exception e) {
					new PopUp("Couldn't check if this is the latest version\nFeel free to close this message!\n\nReason: "
							+ e.getMessage(), false);
				}

				// Update the title based on version
				Editor.getInstance().setTitle(
						editorName() + " (v" + version + (outdated ? " outdated" : (preRelease ? " prerelease" : ""))
								+ ") - By Koupah");

				if (button != null)
					button.setEnabled(true);
			}
		};

		updateCheck.start();
	}
	

	public static String editorName() {
		return AUEditorMain.title;
	}
	
	public static int getWidth(String input, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

		return (int) (font.getStringBounds(input, frc).getWidth());
	}
}
