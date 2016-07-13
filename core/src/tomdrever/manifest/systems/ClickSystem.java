package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.OnClickComponent;
import tomdrever.manifest.components.OnClickListener;

public class ClickSystem extends IteratingSystem {
    private ComponentMapper<OnClickComponent> onClickComponentMap = ComponentMapper.getFor(OnClickComponent.class);
    private ComponentMapper<BoundsComponent> boundsComponentMap = ComponentMapper.getFor(BoundsComponent.class);

    public ClickSystem() {
        super(Family.all(OnClickComponent.class, BoundsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Rectangle entityBounds = boundsComponentMap.get(entity).getBounds();

        if (Gdx.input.isTouched()) {
            // Invert libgdx y co-ord
            if (entityBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                OnClickListener entityOnClick = onClickComponentMap.get(entity).onClickListener;
                entityOnClick.run();
            }
        }
    }
}
