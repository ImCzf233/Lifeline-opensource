package wtf.lifeline.module.modules.player;

import net.minecraft.util.StringUtils;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.misc.EventChatReceive;
import wtf.lifeline.gui.notification.NoticeNotification;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.value.Numbers;
import wtf.lifeline.module.value.Option;
import wtf.lifeline.utils.Helper;

public class AutoHypixel extends Module {

    private final Option<Boolean> autoGG = new Option("AutoGG","AutoGG", true);

    private final Option<Boolean> autoPlay = new Option("AutoPlay","AutoPlay", true);
    private final Numbers<Double> autoPlayDelay = new Numbers("AutoPlay Delay","AutoPlay Delay", 2.5, 8, 2, 0.5);
    private final Option<Boolean> autoHubOnBan = new Option("Auto /l on ban","Auto /l on ban", false);

    public AutoHypixel() {
        super("AutoHypixel", new String[]{}, Category.Player);
        this.addValues(autoGG,  autoPlay, autoPlayDelay, autoHubOnBan);
    }
    @EventTarget
    private void onChatReceived(EventChatReceive e) {
        String message = e.message.getUnformattedText(), strippedMessage = StringUtils.stripControlCodes(message);
        if (autoHubOnBan.getValue() == true && strippedMessage.equals("A player has been removed from your game.")) {
            Helper.send("/lobby");
            NoticeNotification.send( "AutoHypixel", "A player in your lobby got banned.", NoticeNotification.Type.WARNING);
        }
        String m = e.message.toString();
        if (m.contains("EventClick{action=RUN_COMMAND, value='/play ")) {
            if (autoPlay.getValue() == true && !strippedMessage.startsWith("You died!")) {
                Helper.send("/ac " + "GG");
            }
            if (autoPlay.getValue() == true) {
                sendToGame(m.split("action=RUN_COMMAND, value='")[1].split("'}")[0]);
            }
        }
    };
    private void sendToGame(String mode) {
        float delay = autoPlayDelay.getValue().floatValue();
        NoticeNotification.send( "AutoPlay",
                "Sending you to a new game" + (delay > 0 ? " in " + delay + "s" : "") + "!", NoticeNotification.Type.INFO);

    }

}
