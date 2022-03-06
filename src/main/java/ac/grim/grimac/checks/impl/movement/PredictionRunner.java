package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.checks.type.PositionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PositionUpdate;

public class PredictionRunner extends PositionCheck {
    public PredictionRunner(GrimPlayer playerData) {
        super(playerData);
    }

    @Override
    public void onPositionUpdate(final PositionUpdate positionUpdate) {
        if (!player.inVehicle) {
            player.movementCheckRunner.processAndCheckMovementPacket(positionUpdate);
        }
    }
}
