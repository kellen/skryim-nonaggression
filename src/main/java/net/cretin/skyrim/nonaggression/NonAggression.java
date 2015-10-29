package net.cretin.skyrim.nonaggression;

import lev.gui.LTextPane;
import skyproc.GRUP_TYPE;
import skyproc.Mod;
import skyproc.NPC_;
import skyproc.SPGlobal;
import skyproc.gui.SPMainMenuPanel;
import skyproc.gui.SPSettingPanel;
import skyproc.gui.SUMGUI;

import java.awt.*;

/**
 * Patcher which makes all NPCs cowardly and unaggressive.
 */
public class NonAggression extends AbstractSkyProcPatch {
    public NonAggression() {
        super(
                new GRUP_TYPE[]{GRUP_TYPE.NPC_}, null, null,
                "NonAggression", "kellen", "1.0", "Hej!",
                "Makes all NPCs cowardly and unaggressive.",
                new Color(66, 181, 184), new Color(72, 179, 58), new Font("Serif", Font.BOLD, 15)
        );
    }

    public static void main(String[] args) {
        try {
            SPGlobal.createGlobalLog();
            SUMGUI.open(new NonAggression(), args);
        } catch (Exception e) {
            mainErrorHandling(e);
        }
    }

    @Override
    public void runChangesToPatch() throws Exception {
        Mod patch = SPGlobal.getGlobalPatch();
        Mod merger = new Mod(getName() + "Merger", false);
        merger.addAsOverrides(SPGlobal.getDB());

        for (NPC_ npc : merger.getNPCs()) {
            npc.setAggression(NPC_.Aggression.Unaggressive);
            npc.setConfidence(NPC_.Confidence.Cowardly);
            patch.addRecord(npc);
        }
    }

    @Override
    public SPMainMenuPanel getStandardMenu() {
        SPMainMenuPanel settingsMenu = new SPMainMenuPanel(getHeaderColor());
        settingsMenu.setWelcomePanel(new WelcomePanel(settingsMenu, myPatchName, headerColor, settingsColor, settingsFont, welcomeText));
//        dunno what happens if we don't add a menu
//        settingsMenu.addMenu(new OtherSettingsPanel(settingsMenu), false, save, Settings.OTHER_SETTINGS);
        return settingsMenu;
    }

    public class WelcomePanel extends SPSettingPanel {
        LTextPane introText;
        Color settingsColor;
        Font settingsFont;
        String welcomeText;

        public WelcomePanel(SPMainMenuPanel parent_, String patchName, Color headerColor,
                            Color settingsColor, Font settingsFont, String welcomeText) {
            super(parent_, patchName, headerColor);
            this.settingsColor = settingsColor;
            this.settingsFont = settingsFont;
            this.welcomeText = welcomeText;
        }

        @Override
        protected void initialize() {
            super.initialize();

            introText = new LTextPane(settingsPanel.getWidth() - 40, 400, settingsColor);
            introText.setText(welcomeText);
            introText.setEditable(false);
            introText.setFont(settingsFont);
            introText.setCentered();
            setPlacement(introText);
            Add(introText);

            alignRight();
        }
    }
}
