package com.jammingmods.plugin.Helpers;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class Whf_ComponentsHelpers {
    /*public static Component<EntityStore> getOrPutComponent(Ref ref, Store store, ComponentType componentType, Component inst){
        var targetComponent = store.getComponent(ref, componentType);
        if(targetComponent == null){
            store.putComponent(ref, componentType, inst);
            return store.getComponent(ref, componentType);
        } else {
            return targetComponent;
        }
    }

    public static Component<EntityStore> getOrAddComponent(Ref ref, Store store, ComponentType componentType, Component inst){
        var targetComponent = store.getComponent(ref, componentType);
        if(targetComponent == null){
            store.addComponent(ref, componentType, inst);
            return store.getComponent(ref, componentType);
        } else {
            return targetComponent;
        }
    }*/
}
