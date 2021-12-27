package com.zonlykroks.hardcoreex.client.gui;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.zonlykroks.hardcoreex.challenge.Challenge;
import com.zonlykroks.hardcoreex.challenge.manager.ChallengeManager;
import com.zonlykroks.hardcoreex.client.gui.widgets.ChallengeList;
import com.zonlykroks.hardcoreex.network.Networking;
import com.zonlykroks.hardcoreex.network.SetupDonePacket;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
    private static final ITextComponent field_238884_b_ = (new TranslationTextComponent("pack.dropInfo")).mergeStyle(TextFormatting.GRAY);
    private final Screen backScreen;
    private long field_243393_t;
    private ChallengeList leftScreen;
    private ChallengeList rightScreen;
    private final Map<String, ResourceLocation> field_243394_y = Maps.newHashMap();
    private List<Challenge> challenges;
    private List<Challenge> enabled;
    private List<Challenge> disabled;

    public ChallengeScreen(@Nullable Screen destScreenOnDone) {
        super(new TranslationTextComponent("screen.challenges.title"));
        this.backScreen = destScreenOnDone;
//      this.field_241817_w_ = packDirectory;
    }

    public void closeScreen() {
        // Display the back screen.
        Objects.requireNonNull(this.minecraft).displayGuiScreen(this.backScreen);
    }

    protected void init() {
        Button doneButton = this.addButton(new Button(this.width / 2 - 75, this.height - 48, 150, 20, DialogTexts.GUI_DONE, this::done));

        // Todo: allow creating custom challenges using challenge packs.
//      this.addButton(new Button(this.width / 2 - 154, this.height - 48, 150, 20, new TranslationTextComponent("pack.openFolder"), (p_238896_1_) -> {
//         Util.getOSType().openFile(this.field_241817_w_);
//      }, (p_238897_1_, p_238897_2_, p_238897_3_, p_238897_4_) -> {
//         this.renderTooltip(p_238897_2_, field_238885_c_, p_238897_3_, p_238897_4_);
//      }));

        // Force to check the Minecraft instance isn't null.
        assert this.minecraft != null;

        // Create the left screen.
        this.leftScreen = new ChallengeList(this, this.minecraft, 200, this.height, new TranslationTextComponent("pack.available.title"));
        this.leftScreen.setLeftPos(this.width / 2 - 4 - 200);
        this.children.add(this.leftScreen);

        // Create the right screen.
        this.rightScreen = new ChallengeList(this, this.minecraft, 200, this.height, new TranslationTextComponent("pack.selected.title"));
        this.rightScreen.setLeftPos(this.width / 2 + 4);
        this.children.add(this.rightScreen);

        // Reload all.
        this.reloadAll();
    }

    // Todo: add support for custom challenges using .zip / .jar files. (or whatever filetype.)
//   public void tick() {
//      if (this.field_243393_t > 0L && --this.field_243393_t == 0L) {
//         this.reloadAll();
//      }
//   }

    public void reload() {
        // Reloading challenges.
        this.challenges = new ArrayList<>(GameRegistry.findRegistry(Challenge.class).getValues());

        // Enabled / disabled.
        this.enabled = challenges.stream().filter(ChallengeManager.client::isEnabled).collect(Collectors.toList());
        this.disabled = challenges.stream().filter(ChallengeManager.client::isDisabled).collect(Collectors.toList());

        // Reload challenges.
        this.reloadChallenges(this.rightScreen, this.enabled);
        this.reloadChallenges(this.leftScreen, this.disabled);
    }

    private void reloadChallenges(ChallengeList list, List<Challenge> challenges) {
        // Clear challenge in list.
        list.getEventListeners().clear();

        // Add challenge entries.
        for (Challenge challenge : challenges) {
            list.getEventListeners().add(new ChallengeList.ChallengeEntry(Objects.requireNonNull(this.minecraft), list, this, challenge));
        }
    }

    public void reloadAll() {
        this.reload();
//      this.field_243393_t = 0L;
//      this.field_243394_y.clear();
    }

    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);

        // Render lists.
        this.leftScreen.render(matrixStack, mouseX, mouseY, partialTicks);
        this.rightScreen.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render text.
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
//        drawCenteredString(matrixStack, this.font, field_238884_b_, this.width / 2, 20, 16777215);

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
        Networking.sendToServer(new SetupDonePacket());
        this.closeScreen();
    }

    // Todo: allow loading of custom challenges.
//   protected static void func_238895_a_(Minecraft p_238895_0_, List<Path> p_238895_1_, Path p_238895_2_) {
//      MutableBoolean mutableboolean = new MutableBoolean();
//      p_238895_1_.forEach((p_238901_2_) -> {
//         try (Stream<Path> stream = Files.walk(p_238901_2_)) {
//            stream.forEach((p_238900_3_) -> {
//               try {
//                  Util.func_240984_a_(p_238901_2_.getParent(), p_238895_2_, p_238900_3_);
//               } catch (IOException ioexception1) {
//                  field_238883_a_.warn("Failed to copy datapack file  from {} to {}", p_238900_3_, p_238895_2_, ioexception1);
//                  mutableboolean.setTrue();
//               }
//
//            });
//         } catch (IOException ioexception) {
//            field_238883_a_.warn("Failed to copy datapack file from {} to {}", p_238901_2_, p_238895_2_);
//            mutableboolean.setTrue();
//         }
//
//      });
//      if (mutableboolean.isTrue()) {
//         SystemToast.func_238539_c_(p_238895_0_, p_238895_2_.toString());
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
//         ResourceLocation resourcelocation = new ResourceLocation("minecraft", "pack/" + Util.func_244361_a(name, ResourceLocation::validatePathChar) + "/" + Hashing.sha1().hashUnencodedChars(name) + "/icon");
//         NativeImage nativeimage = NativeImage.read(inputstream);
//         textureManager.loadTexture(resourcelocation, new DynamicTexture(nativeimage));
//         return resourcelocation;
//      } catch (FileNotFoundException ignored) {
//      } catch (Exception e) {
//         field_238883_a_.warn("Failed to load icon from pack {}", info.getName(), e);
//      }
//
//      return field_243391_p;
//
//      return info.getChallenge().getTextureLocation();
//   }
//
//   private ResourceLocation getTextureFromInfo(ChallengeInfo p_243395_1_) {
//      return this.field_243394_y.computeIfAbsent(p_243395_1_.getName(), (p_243396_2_) -> {
//         return this.loadChallengeTexture(this.minecraft.getTextureManager(), p_243395_1_);
//      });
//   }
//
//   @OnlyIn(Dist.CLIENT)
//   static class PackDirectoryWatcher implements AutoCloseable {
//      private final WatchService field_243400_a;
//      private final Path field_243401_b;
//
//      public PackDirectoryWatcher(File p_i242061_1_) throws IOException {
//         this.field_243401_b = p_i242061_1_.toPath();
//         this.field_243400_a = this.field_243401_b.getFileSystem().newWatchService();
//
//         try {
//            this.func_243404_a(this.field_243401_b);
//
//            try (DirectoryStream<Path> directorystream = Files.newDirectoryStream(this.field_243401_b)) {
//               for(Path path : directorystream) {
//                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
//                     this.func_243404_a(path);
//                  }
//               }
//            }
//
//         } catch (Exception exception) {
//            this.field_243400_a.close();
//            throw exception;
//         }
//      }
//
//      @Nullable
//      public static ChallengeScreen.PackDirectoryWatcher func_243403_a(File p_243403_0_) {
//         try {
//            return new ChallengeScreen.PackDirectoryWatcher(p_243403_0_);
//         } catch (IOException ioexception) {
//            ChallengeScreen.field_238883_a_.warn("Failed to initialize pack directory {} monitoring", p_243403_0_, ioexception);
//            return null;
//         }
//      }
//
//      private void func_243404_a(Path p_243404_1_) throws IOException {
//         p_243404_1_.register(this.field_243400_a, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
//      }
//
//      public boolean func_243402_a() throws IOException {
//         boolean flag = false;
//
//         WatchKey watchkey;
//         while((watchkey = this.field_243400_a.poll()) != null) {
//            for(WatchEvent<?> watchevent : watchkey.pollEvents()) {
//               flag = true;
//               if (watchkey.watchable() == this.field_243401_b && watchevent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
//                  Path path = this.field_243401_b.resolve((Path)watchevent.context());
//                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
//                     this.func_243404_a(path);
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
//         this.field_243400_a.close();
//      }
//   }
}
