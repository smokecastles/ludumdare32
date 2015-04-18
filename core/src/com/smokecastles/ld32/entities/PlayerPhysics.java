package com.smokecastles.ld32.entities;

import com.smokecastles.ld32.utils.DynamicGameEntity;
import com.smokecastles.ld32.utils.PhysicsComponent;
import com.smokecastles.ld32.utils.WorldPhysics;
import com.smokecastles.ld32.World;

/**
 * Created by juanma on 18/04/15.
 */
public class PlayerPhysics implements PhysicsComponent {
    @Override
    public void update(DynamicGameEntity entity, World world) {
        if (!(entity instanceof Player)) throw new RuntimeException();

        WorldPhysics.checkTiledMapCollisions(entity, world, true);
        checkCollisionsWithEnemies((Player) entity, world);

    }

    private void checkCollisionsWithEnemies(Player dude, World world) {
//        for (Enemy enemy : world.enemies) {
//            if (!enemy.isDisappearing && enemy.bounds.overlaps(dude.bounds)) {
//                if (dude.bounds.y > enemy.position.y && dude.velocity.y < 0) {
//                    dude.bounds.y = enemy.position.y + Enemy.ENEMY_HEIGHT;
//                    dude.position.y = dude.bounds.y + dude.bounds.height / 2;
//
//                    enemy.onCollisionWithPlayer();
//                    dude.onEnemyHit();
//                } else {
//                    // player gets hit
//                }
//            }
//        }
    }
}
