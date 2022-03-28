package com.zonlykroks.hardcoreex.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.screen.ChallengeScreen;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Challenge list for the challenge screen.
 *
 * @author Qboi123
 */
@SuppressWarnings({"unused", "deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ChallengeList extends ExtendedList<ChallengeList.ChallengeEntry> {
    private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/resource_packs.png");
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(HardcoreExtended.MOD_ID, "textures/empty.png");
    private static final ITextComponent INCOMPATIBLE_TEXT = new TranslationTextComponent("challenges.incompatible");
    private static final ITextComponent INCOMPATIBLE_CONFIRM_TITLE = new TranslationTextComponent("challenges.incompatible.confirm.title");
    private final ITextComponent title;
    private final ChallengeScreen screen;
    private final ClientChallengeManager temp;

    /**
     * Constructor for the challenge list widget.
     *
     * @param minecraft the minecraft instance.
     * @param widthIn   the list's width.
     * @param heightIn  the list's height.
     * @param title     the list title.
     */
    public ChallengeList(ChallengeScreen screen, Minecraft minecraft, int widthIn, int heightIn, ITextComponent title) {
        super(minecraft, widthIn, heightIn, 32, heightIn - 55 + 4, 36);
        this.screen = screen;
        this.temp = screen.getManager();
        this.title = title;
        this.centerListVertically = false;
        this.setRenderHeader(true, (int) (9.0F * 1.5F));
    }

    protected void renderHeader(MatrixStack p_230448_1_, int p_230448_2_, int p_230448_3_, Tessellator p_230448_4_) {
        ITextComponent itextcomponent = (new StringTextComponent("")).appendSibling(this.title).mergeStyle(TextFormatting.UNDERLINE, TextFormatting.BOLD);
        this.minecraft.fontRenderer.drawText(p_230448_1_, itextcomponent, (float) (p_230448_2_ + this.width / 2 - this.minecraft.fontRenderer.getStringPropertyWidth(itextcomponent) / 2), (float) Math.min(this.y0 + 3, p_230448_3_), 16777215);
    }

    public int getRowWidth() {
        return this.width;
    }

    protected int getScrollbarPosition() {
        return this.x1 - 6;
    }

    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public static class ChallengeEntry extends ExtendedList.AbstractListEntry<ChallengeEntry> {
        private final ChallengeList list;
        protected final Minecraft mc;
        protected final Screen screen;
        private final Challenge challenge;
        private final IReorderingProcessor reorderingLocalizedName;
        // Todo: allow creation of custom challenges.
//      private final IReorderingProcessor reorderingIncompatible;

        /**
         * Challenge list entry.
         *
         * @param minecraft the minecraft instance.
         * @param list      the challenge list.
         * @param screen    a screen.
         * @param challenge the challenge to hold in the entry,
         */
        public ChallengeEntry(Minecraft minecraft, ChallengeList list, Screen screen, Challenge challenge) {
            this.mc = minecraft;
            this.screen = screen;
            this.challenge = challenge;
            this.list = list;
            this.reorderingLocalizedName = getReordering(minecraft, challenge.getLocalizedName());

            // Todo: allow creation of custom challenges.
//         this.reorderingIncompatible = func_244424_a(p_i241201_1_, ChallengeList.INCOMPATIBLE_TEXT);
        }

        /**
         * Get reordering processor from a text component.
         *
         * @param minecraft the minecraft instance.
         * @param text      the text component to process.
         * @return a {@link IReorderingProcessor reordering processor}.
         */
        private static IReorderingProcessor getReordering(Minecraft minecraft, ITextComponent text) {
            int i = minecraft.fontRenderer.getStringPropertyWidth(text);
            if (i > 157) {
                ITextProperties itextproperties = ITextProperties.func_240655_a_(minecraft.fontRenderer.func_238417_a_(text, 157 - minecraft.fontRenderer.getStringWidth("...")), ITextProperties.func_240652_a_("..."));
                return LanguageMap.getInstance().func_241870_a(itextproperties);
            } else {
                return text.func_241878_f();
            }
        }

        /**
         * Renders the challenge list entry.
         *
         * @param matrixStack  the matrix-stack for rendering.
         * @param unknown1     ...
         * @param subY         delta y in the list.
         * @param subX         delta x in the list.
         * @param unknown2     ...
         * @param unknown3     ...
         * @param mouseX       real mouse x coordinate.
         * @param mouseY       real mouse y coordinate.
         * @param p_230432_9_  ...
         * @param partialTicks the {@link Minecraft#getRenderPartialTicks() render partial ticks}.
         */
        @SuppressWarnings({"CommentedOutCode", "SpellCheckingInspection"})
        public void render(MatrixStack matrixStack, int unknown1, int subY, int subX, int unknown2, int unknown3, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
            // Todo: allow creation of custom challenges.
//         ChallengeCompatibility challengeCompatibility = this.field_214431_d.func_230460_a_();
//         if (!challengeCompatibility.isCompatible()) {
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            AbstractGui.fill(p_230432_1_, p_230432_4_ - 1, p_230432_3_ - 1, p_230432_4_ + p_230432_5_ - 9, p_230432_3_ + p_230432_6_ + 1, -8978432);
//         }

            this.mc.getTextureManager().bindTexture(this.challenge.getTextureLocation());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 32, 32);
            IReorderingProcessor reorderingLocalizedName = this.reorderingLocalizedName;
//         IBidiRenderer ibidirenderer = this.field_243408_f;
            if (this.func_238920_a_() && (this.mc.gameSettings.touchscreen || p_230432_9_)) {
                this.mc.getTextureManager().bindTexture(ChallengeList.ICONS);
                AbstractGui.fill(matrixStack, subX, subY, subX + 32, subY + 32, -1601138544);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                int i = mouseX - subX;
                int j = mouseY - subY;

                // Todo: allow creation of custom challenges.
//            if (!this.field_214431_d.func_230460_a_().isCompatible()) {
//               ireorderingprocessor = this.field_244422_g;
//               ibidirenderer = this.field_244423_h;
//            }

                // Normal arrow button state.
                if (list.temp.isDisabled(this.challenge)) {
                    // Check hover state.
                    if (i < 32) {
                        // Hovered.
                        AbstractGui.blit(matrixStack, subX, subY, 0.0F, 32.0F, 32, 32, 256, 256);
                    } else {
                        // Non-hovered.
                        AbstractGui.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 256, 256);
                    }
                } else if (list.temp.isEnabled(this.challenge)) {
                    // Check hover state.
                    if (i < 16) {
                        // Hovered.
                        AbstractGui.blit(matrixStack, subX, subY, 32.0F, 32.0F, 32, 32, 256, 256);
                    } else {
                        // Non-hovered.
                        AbstractGui.blit(matrixStack, subX, subY, 32.0F, 0.0F, 32, 32, 256, 256);
                    }
                }
            }

            this.mc.fontRenderer.drawTextWithShadow(matrixStack, reorderingLocalizedName, (float) (subX + 32 + 2), (float) (subY + 1), 16777215);
//         ibidirenderer.func_241865_b(matrixStack, subX + 32 + 2, subY + 12, 10, 8421504);
        }

        private boolean func_238920_a_() {
//         return !this.challenge.func_230465_f_() || !this.challenge.func_230466_g_();
            return true;
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            double deltaX = mouseX - (double) this.list.getRowLeft();
            double deltaY = mouseY - (double) this.list.getRowTop(this.list.getEventListeners().indexOf(this));
            if (this.func_238920_a_() && deltaX <= 32.0D) {
                if (list.temp.isDisabled(this.challenge)) {
                    ChallengeCompatibility compatibility = this.challenge.getCompatibility();
                    if (compatibility.isCompatible()) {
                        list.temp.enable(this.challenge);
                    } else {
                        ITextComponent itextcomponent = compatibility.getConfirmMessage();
                        this.mc.displayGuiScreen(new ConfirmScreen((p_238921_1_) -> {
                            this.mc.displayGuiScreen(this.screen);
                            if (p_238921_1_) {
                                list.temp.enable(this.challenge);
                            }

                        }, ChallengeList.INCOMPATIBLE_CONFIRM_TITLE, itextcomponent));
                    }

                    this.list.screen.reloadAll();

                    return true;
                }

                if (deltaX < 16.0D && list.temp.isEnabled(this.challenge)
                ) {
                    list.temp.disable(this.challenge);
                    this.list.screen.reloadAll();
                    return true;
                }
            }

            return false;
        }
    }
}
