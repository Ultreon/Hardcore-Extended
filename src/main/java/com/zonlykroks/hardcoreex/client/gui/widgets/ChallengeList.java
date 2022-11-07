package com.zonlykroks.hardcoreex.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.screen.ChallengeScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Challenge list for the challenge screen.
 *
 * @author Qboi123
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ChallengeList extends ObjectSelectionList<ChallengeList.ChallengeEntry> {
    private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/resource_packs.png");
    @SuppressWarnings("unused")
    private static final Component INCOMPATIBLE_TEXT = new TranslatableComponent("challenges.incompatible");
    private static final Component INCOMPATIBLE_CONFIRM_TITLE = new TranslatableComponent("challenges.incompatible.confirm.title");
    private final Component title;
    private final ChallengeScreen screen;
    private final ClientChallengeManager manager;

    /**
     * Constructor for the challenge list widget.
     *
     * @param minecraft the minecraft instance.
     * @param widthIn   the list's width.
     * @param heightIn  the list's height.
     * @param title     the list title.
     */
    public ChallengeList(ChallengeScreen screen, Minecraft minecraft, int widthIn, int heightIn, Component title) {
        super(minecraft, widthIn, heightIn, 32, heightIn - 55 + 4, 36);
        this.screen = screen;
        this.manager = screen.getManager();
        this.title = title;
        this.centerListVertically = false;
        this.setRenderHeader(true, (int) (9.0F * 1.5F));
    }

    protected void renderHeader(PoseStack p_230448_1_, int p_230448_2_, int p_230448_3_, Tesselator p_230448_4_) {
        Component component = (new TextComponent("")).append(this.title).withStyle(ChatFormatting.UNDERLINE, ChatFormatting.BOLD);
        this.minecraft.font.draw(p_230448_1_, component, (float) (p_230448_2_ + this.width / 2 - this.minecraft.font.width(component) / 2), (float) Math.min(this.y0 + 3, p_230448_3_), 16777215);
    }

    public int getRowWidth() {
        return this.width;
    }

    protected int getScrollbarPosition() {
        return this.x1 - 6;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ChallengeEntry extends ObjectSelectionList.Entry<ChallengeEntry> {
        private static final ResourceLocation DEFAULT_TEXTURE = HardcoreExtended.res("textures/gui/challenges/default.png");
        private final ChallengeList list;
        protected final Minecraft mc;
        protected final Screen screen;
        private final Challenge challenge;
        private final FormattedCharSequence nameReorder;

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
            this.nameReorder = getReordering(minecraft, challenge.getDescription());

            // Todo: allow creation of custom challenges.
//         this.reorderingIncompatible = cacheName(p_i241201_1_, ChallengeList.INCOMPATIBLE_TEXT);
        }

        /**
         * Get reordering processor from a text component.
         *
         * @param minecraft the minecraft instance.
         * @param text      the text component to process.
         * @return a {@link FormattedCharSequence reordering processor}.
         */
        private static FormattedCharSequence getReordering(Minecraft minecraft, Component text) {
            int i = minecraft.font.width(text);
            if (i > 157) {
                FormattedText formattedText = FormattedText.composite(minecraft.font.substrByWidth(text, 157 - minecraft.font.width("...")), FormattedText.of("..."));
                return Language.getInstance().getVisualOrder(formattedText);
            } else {
                return text.getVisualOrderText();
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
         * @param partialTicks the {@link Minecraft#getFrameTime()}  render partial ticks}.
         */
        @SuppressWarnings({"SpellCheckingInspection"})
        public void render(PoseStack matrixStack, int unknown1, int subY, int subX, int unknown2, int unknown3, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
            // Todo: allow creation of custom challenges.
//         ChallengeCompatibility challengeCompatibility = this.pack.getCompatibility();
//         if (!challengeCompatibility.isCompatible()) {
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            AbstractGui.fill(p_230432_1_, p_230432_4_ - 1, p_230432_3_ - 1, p_230432_4_ + p_230432_5_ - 9, p_230432_3_ + p_230432_6_ + 1, -8978432);
//         }

            // Checking for if the texture exists fixes log getting overloaded with exceptions.
            ResourceLocation texture = this.challenge.getTextureLocation();
            RenderSystem.setShaderTexture(0, mc.getResourceManager().hasResource(texture) ? texture : DEFAULT_TEXTURE);

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 32, 32);
            //         IBidiRenderer ibidirenderer = this.descriptionDisplayCache;
            if (this.mc.options.touchscreen || p_230432_9_) {
                RenderSystem.setShaderTexture(0, ChallengeList.ICONS);
                GuiComponent.fill(matrixStack, subX, subY, subX + 32, subY + 32, -1601138544);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                int i = mouseX - subX;

                // Todo: allow creation of custom challenges.
//            if (!this.pack.getCompatibility().isCompatible()) {
//               ireorderingprocessor = this.incompatibleNameDisplayCache;
//               ibidirenderer = this.incompatibleDescriptionDisplayCache;
//            }

                // Normal arrow button state.
                if (list.manager.isDisabled(this.challenge)) {
                    // Check hover state.
                    if (i < 32) {
                        // Hovered.
                        GuiComponent.blit(matrixStack, subX, subY, 0.0F, 32.0F, 32, 32, 256, 256);
                    } else {
                        // Non-hovered.
                        GuiComponent.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 256, 256);
                    }
                } else if (list.manager.isEnabled(this.challenge)) {
                    // Check hover state.
                    if (i < 16) {
                        // Hovered.
                        GuiComponent.blit(matrixStack, subX, subY, 32.0F, 32.0F, 32, 32, 256, 256);
                    } else {
                        // Non-hovered.
                        GuiComponent.blit(matrixStack, subX, subY, 32.0F, 0.0F, 32, 32, 256, 256);
                    }
                }
            }

            this.mc.font.drawShadow(matrixStack, this.nameReorder, (float) (subX + 32 + 2), (float) (subY + 1), 16777215);
//         ibidirenderer.renderLeftAligned(matrixStack, subX + 32 + 2, subY + 12, 10, 8421504);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            double deltaX = mouseX - (double) this.list.getRowLeft();
            if (deltaX <= 32.0D) {
                if (list.manager.isDisabled(this.challenge)) {
                    ChallengeCompatibility compatibility = this.challenge.getCompatibility();
                    if (compatibility.isCompatible()) {
                        list.manager.enable(this.challenge);
                    } else {
                        Component component = compatibility.getConfirmMessage();
                        this.mc.setScreen(new ConfirmScreen((p_238921_1_) -> {
                            this.mc.setScreen(this.screen);
                            if (p_238921_1_) {
                                list.manager.enable(this.challenge);
                            }

                        }, ChallengeList.INCOMPATIBLE_CONFIRM_TITLE, component));
                    }

                    this.list.screen.reloadAll();

                    return true;
                }

                if (deltaX < 16.0D && list.manager.isEnabled(this.challenge)
                ) {
                    list.manager.disable(this.challenge);
                    this.list.screen.reloadAll();
                    return true;
                }
            }

            return false;
        }

        @Override
        public Component getNarration() {
            return new TextComponent("Challenge, " + this.challenge.getDescription());
        }
    }
}
