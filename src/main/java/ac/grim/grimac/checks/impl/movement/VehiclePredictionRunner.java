package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.checks.type.VehicleCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PositionUpdate;
import ac.grim.grimac.utils.anticheat.update.VehiclePositionUpdate;

public class VehiclePredictionRunner extends VehicleCheck {
    public VehiclePredictionRunner(GrimPlayer playerData) {
        super(playerData);
    }

    @Override
    public void process(final VehiclePositionUpdate vehicleUpdate) {
        if (player.bukkitPlayer.getName().startsWith(".")) return;

        // Vehicle onGround = false always
        // We don't do vehicle setbacks because vehicle netcode sucks.
        if (player.inVehicle) {
            player.movementCheckRunner.processAndCheckMovementPacket(new PositionUpdate(vehicleUpdate.getFrom(), vehicleUpdate.getTo(), false, null, vehicleUpdate.isTeleport()));
        }
    }
}
