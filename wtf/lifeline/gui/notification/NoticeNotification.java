package wtf.lifeline.gui.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import wtf.lifeline.gui.font.CFontRenderer;
import wtf.lifeline.gui.font.FontLoaders;
import wtf.lifeline.utils.TimeHelper;
import wtf.lifeline.utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;

public class NoticeNotification {
    private String message;
    private String header;
    private TimeHelper timer;
    private double lastY;
    private double posY;
    private double width;
    private double height;
    private double animationX;
    private int color;
    private int imageWidth;
    private final ResourceLocation image;
    private long stayTime;
    Minecraft mc = Minecraft.getMinecraft();

    public static ArrayList notifications = new ArrayList();

    public NoticeNotification(final String header,final String message, final Type type) {
        this.message = message;
        this.header = header;
        (this.timer = new TimeHelper()).reset();
        CFontRenderer font = FontLoaders.kiona20;
        this.width = font.getStringWidth(message);
        this.height = 10.5;
        this.animationX = this.width;
        this.stayTime = 3000L;
        this.imageWidth = 20;
        this.posY = -1.0;
        this.image = new ResourceLocation("windy/mainmenu/noti/" + type.name().toLowerCase() + ".png");
        this.color = new Color(40, 40, 40).getRGB();
    }

    public static void send(String header,String message, Type type) {

        if (notifications.size() > 8) {
            notifications.remove(0);
        }
        notifications.add(new NoticeNotification(header,message, type));
    }

    public static void drawNotifications() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double startY = (double) (res.getScaledHeight() - 25);
        double lastY = startY;

        for (int i = 0; i < notifications.size(); ++i) {
            NoticeNotification not = (NoticeNotification) notifications.get(i);
            if (not.shouldDelete()) {
                notifications.remove(i);
            }

            not.draw(startY, lastY);
            startY -= not.getHeight() + 30.0D;
        }

    }

    public void draw(final double getY, final double lastY) {
        this.lastY = lastY;
        this.animationX = RenderUtil.getAnimationState(this.animationX, this.isFinished() ? this.width : 0.0,
                Math.max(this.isFinished() ? 200 : 30,
                        Math.abs(this.animationX - (this.isFinished() ? this.width : 0.0)) * 5.0));
        if (this.posY == -1.0) {
            this.posY = getY;
        } else {
            this.posY = RenderUtil.getAnimationState(this.posY, getY, 100.0);
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final int x1 = (int) (res.getScaledWidth() - this.width + this.animationX);
        final int x2 = (int) (res.getScaledWidth() + this.animationX);
        final int y1 = (int) this.posY - 40;
        final int y2 = (int) (y1 + this.height + 20);

        RenderUtil.drawRect(x1 - 30, y1 + 10, x2 - 3, y2 + 2, color);
        RenderUtil.drawCustomImage((int) (x1 + (this.height - this.imageWidth) / 2.0) - 21,
                y1 + (int) ((this.height - this.imageWidth) / 2.0) + 19, this.imageWidth / 2 + 2, this.imageWidth / 2 + 2, this.image);
        CFontRenderer font = FontLoaders.kiona18;
        font.drawString(this.message, (float) (x1 + this.width / 2.0) - 47,
                (float) (y1 + this.height / 2 - font.getStringHeight(this.message) / 2 + 15.5),
                new Color(240, 240, 240).getRGB());
        RenderUtil.drawRect(x1 - 30, y2 + 2, x2 - 3, y2 + 3, new Color(240, 240, 240).getRGB());
    }

    public boolean shouldDelete() {
        return this.isFinished() && this.animationX >= this.width;
    }

    private boolean isFinished() {
        return this.timer.isDelayComplete(this.stayTime) && this.posY == this.lastY;
    }

    public double getHeight() {
        return this.height;
    }

    public enum Type {
        SUCCESS("SUCCESS", 0), INFO("INFO", 1), WARNING("WARNING", 2), ERROR("ERROR", 3);

        private Type(final String s, final int n) {
        }
    }

    public static void clear() {
        notifications.clear();
    }
}
