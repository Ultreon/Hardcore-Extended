package com.zOnlyKroks.hardcoreex.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.text2speech.Narrator;
import com.zOnlyKroks.hardcoreex.HardcoreExtended;
import com.zOnlyKroks.hardcoreex.challenge.Challenge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * The delete world screen.
 *
 * @author Qboi123
 */
@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = HardcoreExtended.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ChallengeFailedScreen extends Screen {
    private final Challenge challenge;
    /**
     * The integer value containing the number of ticks that have passed since the player's death
     */
//    private final ITextComponent failedCause;
    private ITextComponent scoreText;

    /**
     * Delete world constructor.
     *
     * @param challenge the failed challenge.
     */
    public ChallengeFailedScreen(Challenge challenge) {
        super(new TranslationTextComponent("hardcoreex.failed_challenge.title", challenge.getLocalizedName()));
        
        // Assign variables.
        this.challenge = challenge;
//        this.failedCause = new TranslationTextComponent("hardcoreex.failed_challenge.message");
    }

    /**
     * Initialize the gui.
     */
    protected void init() {
        // Call super method for vanilla things to do to.
        super.init();

        // Get narrator status, narrator status can be modified using Ctrl+B in MC.
        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        // Check the narrator status to be system or all. The narrator is off by default.
        // This if statement will only true when enabled and set to system or all.
        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            // Let the narrator say things.
            Narrator.getNarrator().say("You failed the " + challenge.getLocalizedName() + " challenge!", true);
        }

        // Clear widgets from older screen. So there will no copies of older instances.
        this.buttons.clear();
        this.children.clear();
        // Respawn player.
        if (!getMinecraft().isIntegratedServerRunning()) {
            throw new IllegalStateException("Expected to have integrated server running!");
        }

        if (this.minecraft.player == null) {
            throw new NullPointerException("Client player is null!");
        }

        if (this.minecraft.getIntegratedServer() == null) {
            throw new NullPointerException("Integrated server is null!");
        }

        // Add button for respawn.
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, new TranslationTextComponent("deathScreen.spectate"), (p_213021_1_) -> {
            // Respawn player.
            if (!getMinecraft().isIntegratedServerRunning()) {
                throw new IllegalStateException("Expected to have integrated server running!");
            }

            if (this.minecraft.player == null) {
                throw new NullPointerException("Client player is null!");
            }

            if (this.minecraft.getIntegratedServer() == null) {
                throw new NullPointerException("Integrated server is null!");
            }

            // Respawn player.
            this.minecraft.player.respawnPlayer();

            // Set to spectator.
            Objects.requireNonNull(this.minecraft.getIntegratedServer().getPlayerList().getPlayerByUUID(this.minecraft.player.getUniqueID())).setGameType(GameType.SPECTATOR);

            // Close screen.
            this.minecraft.displayGuiScreen(null);
        }));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96, 200, 20, new TranslationTextComponent("hardcoreex.failed_challenge.delete"), (p_213020_1_) -> {
            // Delete world.
            this.unloadAndDeleteWorld();
        }));

        // Translation text component for score display.
        this.scoreText = (new TranslationTextComponent("deathScreen.score")).appendString(": ").append((new StringTextComponent(Integer.toString(Objects.requireNonNull(this.minecraft.player).getScore()))).mergeStyle(TextFormatting.YELLOW));
    }

    /**
     * Method that deletes the world/
     */
    private void unloadAndDeleteWorld() {
        // Minecraft must be non-null, so require it.
        Objects.requireNonNull(this.minecraft);
        
        // Get world name from integrated server (local server for single-player).
        IntegratedServer integratedServer = this.minecraft.getIntegratedServer();
        String worldName = Objects.requireNonNull(integratedServer).getServerConfiguration().getWorldName();
        
        // Check if the world isn't null.
        if (this.minecraft.world != null) {
            // Send quit and disconnect packet.
            this.minecraft.world.sendQuittingDisconnectingPacket();
        }

        // Unload world, so the world isn't loaded when deleting, because if we didn't deleting the world would cause
        //  a IOException to occur.
        this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));

        // Try to delete world.
        try {
            // Get save loader.
            SaveFormat saveLoader = this.minecraft.getSaveLoader();
            
            // Get the level save from the name we got earlier from the integrated server.
            SaveFormat.LevelSave levelSave = saveLoader.getLevelSave(worldName);
            levelSave.deleteSave(); // Todo: if not working try Minecraft#deleteWorld(...)
        } catch (IOException e) {
            // Something went terribly wrong.
            // Print stack trace. Todo: transform into a ReportedException when needed.
            e.printStackTrace();
        }

        // Show the main menu.
        this.minecraft.displayGuiScreen(new MainMenuScreen());
    }

    /**
     * The render method, called when rendering the Minecraft Window.
     * 
     * @param matrixStack the {@link MatrixStack matrix stack}.
     * @param mouseX the mouse x position.
     * @param mouseY the mouse y position.
     * @param partialTicks the {@link Minecraft#getRenderPartialTicks() render partial ticks}.
     */
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Fill background, that darkening. The color is ARGB (Alpha, Red, Green Blue)
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0x60500000, 0xa0803030);
        
        // Push matrix, we need to do that otherwise it will conflict with other widgets / buttons / texts.
        RenderSystem.pushMatrix();
        
        // Scale the render matrix, in fact it scales the things after this.
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        
        // Draw a string to the window, the title, the message that the player died.
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2 / 2, 30, 16777215);
        
        // Pop the matrix, used to revert the scaling for objects after this. So the buttons will not be scaled.
        RenderSystem.popMatrix();

        // Draw the score information.
        drawCenteredString(matrixStack, this.font, this.scoreText, this.width / 2, 100, 16777215);

        // Call super method.
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Flag for that the game will be paused in this screen.
     * In this case, not.
     * 
     * @return false.
     */
    public boolean isPauseScreen() {
        return false;
    }
}
