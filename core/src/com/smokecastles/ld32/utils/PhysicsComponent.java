package com.smokecastles.ld32.utils;

import com.smokecastles.ld32.entities.World;
import com.smokecastles.ld32.entities.DynamicGameEntity;

public interface PhysicsComponent {
    void update(DynamicGameEntity entity, World world);
}
