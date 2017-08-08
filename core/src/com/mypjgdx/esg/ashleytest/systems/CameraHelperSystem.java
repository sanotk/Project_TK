package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.components.CameraHelperComponent;
import com.mypjgdx.esg.ashleytest.components.TransformComponent;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class CameraHelperSystem extends IteratingSystem {


    public CameraHelperSystem() {
        super(Family.all(CameraHelperComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraHelperComponent cameraHelper = Mappers.cameraHelper.get(entity);
        TransformComponent transform = Mappers.transform.get(entity);

        if (cameraHelper.target != null) {
            followTarget(cameraHelper, transform);
        }
        keepCameraInBound(cameraHelper, transform);
        applyToCamera(cameraHelper, transform);
    }

    private void followTarget(CameraHelperComponent cameraHelper, TransformComponent transform) {
        TransformComponent targetTransform = Mappers.transform.get(cameraHelper.target);

        transform.position.x += (targetTransform.position.x + targetTransform.origin.x - transform.position.x) * cameraHelper.speed;
        transform.position.y += (targetTransform.position.y + targetTransform.origin.y - transform.position.y) * cameraHelper.speed;

    }

    private void keepCameraInBound(CameraHelperComponent cameraHelper, TransformComponent transform) {
        final float halfCameraWidth = cameraHelper.camera.viewportWidth * 0.5f;
        final float halfCameraHeight = cameraHelper.camera.viewportHeight * 0.5f;

        if (transform.position.x > cameraHelper.rightMost - halfCameraWidth * cameraHelper.zoom) {
            transform.position.x = cameraHelper.rightMost - halfCameraWidth * cameraHelper.zoom;
        } else if (transform.position.x < cameraHelper.leftMost + halfCameraWidth * cameraHelper.zoom)
            transform.position.x = cameraHelper.leftMost + halfCameraWidth * cameraHelper.zoom;

        if (transform.position.y > cameraHelper.topMost - halfCameraHeight * cameraHelper.zoom)
            transform.position.y = cameraHelper.topMost - halfCameraHeight * cameraHelper.zoom;
        else if (transform.position.y < cameraHelper.bottomMost + halfCameraHeight * cameraHelper.zoom)
            transform.position.y = cameraHelper.bottomMost + halfCameraHeight * cameraHelper.zoom;
    }

    private void applyToCamera(CameraHelperComponent cameraHelper, TransformComponent transform) {
        cameraHelper.camera.position.x = transform.position.x;
        cameraHelper.camera.position.y = transform.position.y;
        cameraHelper.camera.zoom = cameraHelper.zoom;
        cameraHelper.camera.update();
    }
}
