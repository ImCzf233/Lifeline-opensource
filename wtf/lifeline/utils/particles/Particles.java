/*
 Copyright Alan Wood 2021
 None of this code to be reused without my written permission
 Intellectual Rights owned by Alan Wood
 */
package wtf.lifeline.utils.particles;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import wtf.lifeline.utils.render.RenderUtil;
import wtf.lifeline.utils.sub.EvictingList;
import wtf.lifeline.utils.sub.TimeUtil;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.attack.EventFight;
import wtf.lifeline.events.rendering.EventRender3D;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.value.Numbers;
import wtf.lifeline.module.value.Option;

import java.util.List;

public class Particles extends Module {

    private final Numbers<Double> amount = new Numbers("Amount", "Amount", 10, 1, 20, 1);
    private final Option<Boolean> physics = new Option("Physics", "Physics", true);

    private final List<Particle> particles = new EvictingList<>(100);
    private final TimeUtil timer = new TimeUtil();
    private EntityLivingBase target;

    public Particles() {
        super("Particles", new String[]{}, Category.RENDER);
        addValues(amount,physics);
    }

    @EventTarget
    public void onAttackEvent(final EventFight event) {
        if (event.getTarget() instanceof EntityLivingBase)
            target = (EntityLivingBase) event.getTarget();
    }

    @EventTarget
    public void onPreMotion(final EventUpdate event) {
        if(event.isPre()) {
            if (target != null && target.hurtTime >= 9 && mc.thePlayer.getDistance(target.posX, target.posY, target.posZ) < 10) {
                for (int i = 0; i < amount.getValue(); i++)
                    particles.add(new Particle(new Vec3(target.posX + (Math.random() - 0.5) * 0.5, target.posY + Math.random() * 1 + 0.5, target.posZ + (Math.random() - 0.5) * 0.5)));

                target = null;
            }
        }
    }

    @EventTarget
    public void onRender3DEvent(final EventRender3D event) {
        if (particles.isEmpty())
            return;

        for (int i = 0; i <= timer.getElapsedTime() / 1E+11; i++) {
            if (physics.getValue()==true)
                particles.forEach(Particle::update);
            else
                particles.forEach(Particle::updateWithoutPhysics);
        }

        particles.removeIf(particle -> mc.thePlayer.getDistanceSq(particle.getPosition().xCoord, particle.getPosition().yCoord, particle.getPosition().zCoord) > 50 * 10);

        timer.reset();

        RenderUtil.renderParticles(particles);
    }
}