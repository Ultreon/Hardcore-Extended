package com.zonlykroks.hardcoreex.client.gui.screen;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.client.ClientChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.widgets.ChallengeList;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.packets.EnableChallengePacket;
import com.zonlykroks.hardcoreex.network.packets.StartChallengesPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings({"CommentedOutCode", "unused", "SpellCheckingInspection"})
@OnlyIn(Dist.CLIENT)
public class ChallengeScreen extends Screen {
    private static final Component DRAG_AND_DROP = (new TranslatableComponent("pack.dropInfo")).withStyle(ChatFormatting.GRAY);
    private final Screen backScreen;
    private final ClientChallengeManager temp;
    private long ticksToReload;
    private ChallengeList leftScreen;
    private ChallengeList rightScreen;
    private final Map<String, ResourceLocation> packIcons = Maps.newHashMap();
    private List<Challenge> challenges;
    private List<Challenge> enabled;
    private List<Challenge> disabled;

    public ChallengeScreen(@Nullable Screen destScreenOnDone) {
        super(new TranslatableComponent("screen.challenges.title"));
        this.backScreen = destScreenOnDone;
//      this.packDir = packDirectory;
        this.temp = ClientChallengeManager.temp();
    }

    public void onClose() {
        // Display the back screen.
        Objects.requireNonNull(this.minecraft).setScreen(this.backScreen);
    }

    protected void init() {
        Button doneButton = this.addButton(new Button(this.width / 2 - 75, this.height - 48, 150, 20, CommonComponents.GUI_DONE, this::done));

        // Todo: allow creating custom challenges using challenge packs.
//      this.addButton(new Button(this.width / 2 - 154, this.height - 48, 150, 20, new TranslationTextComponent("pack.openFolder"), (p_238896_1_) -> {
//         Util.getOSType().openFile(this.packDir);
//      }, (p_238897_1_, p_238897_2_, p_238897_3_, p_238897_4_) -> {
//         this.renderTooltip(p_238897_2_, DIRECTORY_BUTTON_TOOLTIP, p_238897_3_, p_238897_4_);
//      }));

        // Force to check the Minecraft instance isn't null.
        assert this.minecraft != null;

        // Create the left screen.
        this.leftScreen = new ChallengeList(this, this.minecraft, 200, this.height, new TranslatableComponent("pack.available.title"));
        this.leftScreen.setLeftPos(this.width / 2 - 4 - 200);
        this.children.add(this.leftScreen);

        // Create the right screen.
        this.rightScreen = new ChallengeList(this, this.minecraft, 200, this.height, new TranslatableComponent("pack.selected.title"));
        this.rightScreen.setLeftPos(this.width / 2 + 4);
        this.children.add(this.rightScreen);

        // Reload all.
        this.reloadAll();
    }

    // Todo: add support for custom challenges using .zip / .jar files. (or whatever filetype.)
//   public void tick() {
//      if (this.ticksToReload > 0L && --this.ticksToReload == 0L) {
//         this.reloadAll();
//      }
//   }

    public void reload() {
        // Reloading challenges.
        this.challenges = new ArrayList<>(GameRegistry.findRegistry(Challenge.class).getValues());

        // Enabled / disabled.
        this.enabled = challenges.stream().filter(temp::isEnabled).collect(Collectors.toList());
        this.disabled = challenges.stream().filter(temp::isDisabled).collect(Collectors.toList());

        // Reload challenges.
        this.reloadChallenges(this.rightScreen, this.enabled);
        this.reloadChallenges(this.leftScreen, this.disabled);
    }

    private void reloadChallenges(ChallengeList list, List<Challenge> challenges) {
        // Clear challenge in list.
        list.children().clear();

        // Add challenge entries.
        for (Challenge challenge : challenges) {
            list.children().add(new ChallengeList.ChallengeEntry(Objects.requireNonNull(this.minecraft), list, this, challenge));
        }
    }

    public void reloadAll() {
        this.reload();
    }

    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);

        // Render lists.
        this.leftScreen.render(matrixStack, mouseX, mouseY, partialTicks);
        this.rightScreen.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render text.
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
//        drawCenteredString(matrixStack, this.font, DRAG_AND_DROP, this.width / 2, 20, 16777215);

        // Render super object.
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public List<Challenge> getEnabled() {
        return enabled;
    }

    public List<Challenge> getDisabled() {
        return disabled;
    }

    private void done(Button p_238903_1_) {
        for (Challenge challenge : enabled) {
            Networking.sendToServer(new EnableChallengePacket(challenge.getRegistryName()));
        }
        Networking.sendToServer(new StartChallengesPacket());
        this.onClose();
    }

    public ClientChallengeManager getManager() {
        return temp;
    }

    // Todo: allow loading of custom challenges.
//   protected static void copyPacks(Minecraft p_238895_0_, List<Path> p_238895_1_, Path p_238895_2_) {
//      MutableBoolean mutableboolean = new MutableBoolean();
//      p_238895_1_.forEach((p_238901_2_) -> {
//         try (Stream<Path> stream = Files.walk(p_238901_2_)) {
//            stream.forEach((p_238900_3_) -> {
//               try {
//                  Util.copyBetweenDirs(p_238901_2_.getParent(), p_238895_2_, p_238900_3_);
//               } catch (IOException ioexception1) {
//                  LOGGER.warn("Failed to copy datapack file  from {} to {}", p_238900_3_, p_238895_2_, ioexception1);
//                  mutableboolean.setTrue();
//               }
//
//            });
//         } catch (IOException ioexception) {
//            LOGGER.warn("Failed to copy datapack file from {} to {}", p_238901_2_, p_238895_2_);
//            mutableboolean.setTrue();
//         }
//
//      });
//      if (mutableboolean.isTrue()) {
//         SystemToast.onPackCopyFailure(p_238895_0_, p_238895_2_.toString());
//      }
//
//   }
//
//   @SuppressWarnings({"unused", "SpellCheckingInspection"})
//   private ResourceLocation loadChallengeTexture(TextureManager textureManager, ChallengeInfo info) {
//      try (
//              Challenge challenge = info.getChallenge();
//              InputStream inputstream = challenge.getRootResourceStream("pack.png");
//      ) {
//         String name = info.getName().getString();
//         ResourceLocation resourcelocation = new ResourceLocation("minecraft", "pack/" + Util.sanitizeName(name, ResourceLocation::validatePathChar) + "/" + Hashing.sha1().hashUnencodedChars(name) + "/icon");
//         NativeImage nativeimage = NativeImage.read(inputstream);
//         textureManager.loadTexture(resourcelocation, new DynamicTexture(nativeimage));
//         return resourcelocation;
//      } catch (FileNotFoundException ignored) {
//      } catch (Exception e) {
//         LOGGER.warn("Failed to load icon from pack {}", info.getName(), e);
//      }
//
//      return DEFAULT_ICON;
//
//      return info.getChallenge().getTextureLocation();
//   }
//
//   private ResourceLocation getTextureFromInfo(ChallengeInfo p_243395_1_) {
//      return this.packIcons.computeIfAbsent(p_243395_1_.getName(), (p_243396_2_) -> {
//         return this.loadChallengeTexture(this.minecraft.getTextureManager(), p_243395_1_);
//      });
//   }
//
//   @OnlyIn(Dist.CLIENT)
//   static class PackDirectoryWatcher implements AutoCloseable {
//      private final WatchService watcher;
//      private final Path packPath;
//
//      public PackDirectoryWatcher(File p_i242061_1_) throws IOException {
//         this.packPath = p_i242061_1_.toPath();
//         this.watcher = this.packPath.getFileSystem().newWatchService();
//
//         try {
//            this.watchDir(this.packPath);
//
//            try (DirectoryStream<Path> directorystream = Files.newDirectoryStream(this.packPath)) {
//               for(Path path : directorystream) {
//                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
//                     this.watchDir(path);
//                  }
//               }
//            }
//
//         } catch (Exception exception) {
//            this.watcher.close();
//            throw exception;
//         }
//      }
//
//      @Nullable
//      public static ChallengeScreen.PackDirectoryWatcher create(File p_243403_0_) {
//         try {
//            return new ChallengeScreen.PackDirectoryWatcher(p_243403_0_);
//         } catch (IOException ioexception) {
//            ChallengeScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", p_243403_0_, ioexception);
//            return null;
//         }
//      }
//
//      private void watchDir(Path p_243404_1_) throws IOException {
//         p_243404_1_.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
//      }
//
//      public boolean pollForChanges() throws IOException {
//         boolean flag = false;
//
//         WatchKey watchkey;
//         while((watchkey = this.watcher.poll()) != null) {
//            for(WatchEvent<?> watchevent : watchkey.pollEvents()) {
//               flag = true;
//               if (watchkey.watchable() == this.packPath && watchevent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
//                  Path path = this.packPath.resolve((Path)watchevent.context());
//                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
//                     this.watchDir(path);
//                  }
//               }
//            }
//
//            watchkey.reset();
//         }
//
//         return flag;
//      }
//
//      public void close() throws IOException {
//         this.watcher.close();
//      }
//   }
}
