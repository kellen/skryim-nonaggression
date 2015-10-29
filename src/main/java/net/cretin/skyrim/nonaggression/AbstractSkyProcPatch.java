package net.cretin.skyrim.nonaggression;

import lev.gui.LSaveFile;
import org.apache.commons.lang3.NotImplementedException;
import skyproc.*;
import skyproc.gui.SPMainMenuPanel;
import skyproc.gui.SUM;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * You must implement public static void main(String[] args) in your concrete class.
 */
public abstract class AbstractSkyProcPatch implements SUM {

    public final SkyProcSave save;
    public final GRUP_TYPE[] importRequests;
    public final URL logo;

    public final String myPatchName;
    public final String authorName;
    public final String version;
    public final String welcomeText;
    public final String descriptionToShowInSUM;

    public Color headerColor;
    public Color settingsColor;
    public Font settingsFont;

    AbstractSkyProcPatch(GRUP_TYPE[] importRequests, SkyProcSave save, URL logo,
                         String myPatchName, String authorName, String version, String welcomeText, String descriptionToShowInSUM,
                         Color headerColor, Color settingsColor, Font settingsFont) {
        this.importRequests = importRequests;
        this.save = save;
        this.logo = logo;
        this.myPatchName = myPatchName;
        this.authorName = authorName;
        this.version = version;
        this.welcomeText = welcomeText;
        this.descriptionToShowInSUM = descriptionToShowInSUM;
        this.headerColor = headerColor;
        this.settingsColor = settingsColor;
        this.settingsFont = settingsFont;
    }

    /**
     * - runChangesToPatch(), where you put all the processing code and add records to the output patch.
     *
     * This is where you should write the bulk of your code.
     * Write the changes you would like to make to the patch,
     * but DO NOT export it.  Exporting is handled internally.
     */
    @Override
    public abstract void runChangesToPatch() throws Exception;

    /**
     * This function labels any record types that you "multiply".
     * For example, if you took all the armors in a mod list and made 3 copies,
     * you would put ARMO here.
     * This is to help monitor/prevent issues where multiple SkyProc patchers
     * multiply the same record type to yeild a huge number of records.
     */
    @Override
    public GRUP_TYPE[] dangerousRecordReport() {
        return new GRUP_TYPE[0]; // None
    }

    /**
     * - getStandardMenu(), where you set up the GUI
     *
     * This is where you add panels to the main menu.
     * First create custom panel classes (as shown by YourFirstSettingsPanel),
     * Then add them here.
     */
    @Override
    public abstract SPMainMenuPanel getStandardMenu();

    @Override
    public String getName() {
        return myPatchName;
    }

    @Override
    public GRUP_TYPE[] importRequests() {
        return importRequests;
    }

    @Override
    public boolean importAtStart() {
        return false;
    }

    @Override
    public boolean hasStandardMenu() {
        return true;
    }

    // Usually false unless you want to make your own GUI
    @Override
    public boolean hasCustomMenu() {
        return false;
    }

    @Override
    public JFrame openCustomMenu() {
        throw new NotImplementedException("Not implemented.");
    }

    @Override
    public boolean hasLogo() {
        return logo != null;
    }

    @Override
    public URL getLogo() {
        if(hasLogo()) {
            return logo;
        }
        throw new NotImplementedException("Not implemented.");
    }

    @Override
    public boolean hasSave() {
        return save != null;
    }

    @Override
    public LSaveFile getSave() {
        if(hasSave()) {
            return save;
        }
        throw new NotImplementedException("Not implemented.");
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public ModListing getListing() {
        return new ModListing(getName(), false);
    }

    @Override
    public Mod getExportPatch() {
        Mod out = new Mod(getListing());
        out.setAuthor(authorName);
        return out;
    }

    @Override
    public Color getHeaderColor() {
        return headerColor;
    }

    // Add any custom checks to determine if a patch is needed.
    // On Automatic Variants, this function would check if any new packages were
    // added or removed.
    @Override
    public boolean needsPatching() {
        return false;
    }

    // This function runs when the program opens to "set things up"
    // It runs right after the save file is loaded, and before the GUI is displayed
    @Override
    public void onStart() throws Exception {
    }

    // This function runs right as the program is about to close.
    @Override
    public void onExit(boolean patchWasGenerated) throws Exception {
    }

    // Add any mods that you REQUIRE to be present in order to patch.
    @Override
    public ArrayList<ModListing> requiredMods() {
        return new ArrayList<>(0);
    }

    @Override
    public String description() {
        return descriptionToShowInSUM;
    }

    /**
     * For usage in main class catch block.
     *
     * @param e The thrown exception
     */
    public static void mainErrorHandling(Exception e) {
        // If a major error happens, print it everywhere and display a message box.
        System.err.println(e.toString());
        System.err.println("BLEH");
        e.printStackTrace();
        SPGlobal.logException(e);
        JOptionPane.showMessageDialog(null, "There was an exception thrown during program execution: '" + e + "'  Check the debug logs or contact the author.");
        SPGlobal.closeDebug();
    }
}
