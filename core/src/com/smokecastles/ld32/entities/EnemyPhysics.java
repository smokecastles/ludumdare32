package com.smokecastles.ld32.entities;

import com.smokecastles.ld32.World;
import com.smokecastles.ld32.utils.DynamicGameEntity;
import com.smokecastles.ld32.utils.PhysicsComponent;
import com.smokecastles.ld32.utils.WorldPhysics;

/**
 * Created by juanma on 18/04/15.
 */
public class EnemyPhysics implements PhysicsComponent {
    @Override
    public void update(DynamicGameEntity entity, World world) {
        if (!(entity instanceof Enemy)) throw new RuntimeException();

        WorldPhysics.checkTiledMapCollisions(entity, world, true);
    }
}