package ac.grim.grimac.checks.impl.prediction;

import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import org.bukkit.GameMode;

@CheckData(name = "GroundSpoof", configName = "GroundSpoof", setback = 10, decay = 0.01, dontAlertUntil = 20, alertInterval = 20)
public class NoFallB extends PostPredictionCheck {

    public NoFallB(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        // Exemptions
        // Don't check players in spectator
        if (PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_8) && player.gamemode == GameMode.SPECTATOR)
            return;

        if (player.bukkitPlayer.getName().startsWith(".")) return;

        // And don't check this long list of ground exemptions
        if (player.exemptOnGround()) return;
        // Don't check if the player was on a ghost block
        if (player.getSetbackTeleportUtil().blockOffsets) return;
        // Viaversion sends wrong ground status... (doesn't matter but is annoying)
        if (player.packetStateData.lastPacketWasTeleport) return;

        boolean invalid = player.clientClaimsLastOnGround != player.onGround;

        if (invalid) {
            flagWithSetback();
            alert("claimed " + player.clientClaimsLastOnGround, "GroundSpoof (P)", formatViolations());
            player.checkManager.getNoFall().flipPlayerGroundStatus = true;
        }
    }
}
