package com.smokecastles.ld32.entities;

import com.smokecastles.ld32.utils.PhysicsComponent;
import com.smokecastles.ld32.utils.WorldPhysics;

public class EnemyPhysics implements PhysicsComponent {
    @Override
    public void update(DynamicGameEntity entity, World world) {
        if (!(entity instanceof Enemy)) throw new RuntimeException();

        WorldPhysics.checkTiledMapCollisions(entity, world, true);
    }
}
