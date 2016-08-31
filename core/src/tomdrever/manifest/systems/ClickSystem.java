package tomdrever.manifest.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.OnClickComponent;
import tomdrever.manifest.components.SpriteComponent;

public class ClickSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<OnClickComponent> onClickComponentMap = ComponentMapper.getFor(OnClickComponent.class);
    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, OnClickComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            final Entity entity = entities.get(i);
            Rectangle entityBounds = spriteComponentMap.get(entity).getBounds();
            if (onClickComponentMap.get(entity).isActive()) {
                // Invert libgdx y co-ord
                if (entityBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    final OnClickComponent.OnClick entityOnClick = onClickComponentMap.get(entity).getOnClick();

                    entityOnClick.run(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                }
            }
        }

        // After processing all entities that might want to respond to the detected click,
        // stop processing
        setProcessing(false);
    }
}
