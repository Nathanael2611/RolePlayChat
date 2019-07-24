package fr.nathanael2611.roleplaychat.client.gui;

import fr.nathanael2611.roleplaychat.network.PacketSetRolePlayName;
import fr.nathanael2611.roleplaychat.network.RolePlayChatPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class GuiChangeRPName extends GuiScreen {

    private String roleplayName;
    private GuiTextField field;

    public GuiChangeRPName(String roleplayName){
        this.roleplayName = roleplayName;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.field = new GuiTextField(
                0, fontRenderer, width/2-80, height/2-40, 160, 20
        );
        this.field.mouseClicked(this.field.x+2, this.field.y+2, 1);
        this.field.setText(this.roleplayName);
        this.buttonList.add(
                new GuiButton(0, width/2-80, height/2+20, 160, 20,"Change")
        );
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(Mouse.isGrabbed())Mouse.setGrabbed(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRenderer, "Nom RP:", width/2, height/2-50, Color.WHITE.getRGB());
        GlStateManager.pushMatrix();
        GlStateManager.translate(width/2-100, height/2-100, -1);
        Gui.drawRect(
                0, 0, 200, 200, new Color(0, 0, 0, 100).getRGB()
        );
        Gui.drawRect(
                -2, -2, 202, 202, new Color(0, 0, 0, 100).getRGB()
        );
        GlStateManager.popMatrix();
        this.field.drawTextBox();

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.field.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 0){
            RolePlayChatPacketHandler.getNetwork().sendToServer(
                    new PacketSetRolePlayName(this.field.getText())
            );
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
