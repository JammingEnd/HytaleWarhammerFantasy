package com.jammingmods.plugin.Components.PlayerTraitComponents;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.EnumCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Random;

public class Whf_SkavenGambleDamageComponent implements Component<EntityStore> {
    private boolean Rolled;
    private float DamageMultiplierRange = 2.0f;
    private float DamageMultiplier = 1.0f;
   private Double Chance;
   public Double getChance() {
      return this.Chance;
   }
   private float SelfDamage = 15;  // flat
   public  float getSelfDamage() {
       return this.SelfDamage;
   }

   public void Roll(){
       Random rand = new Random();
       if(this.Chance >= 1){
           Rolled = true;
       } else if (this.Chance <= 0.01) {
           Rolled = false;
       } else  {
           int roll = rand.nextInt(100);
           double d_roll = roll * 0.01;
           if (this.Chance >= d_roll) {
               Rolled = true;
           } else {
               Rolled = false;
           }
       }
       this.DamageMultiplier = rand.nextFloat(DamageMultiplierRange);
   }

   public float getDamageMultiplier() {
       return this.DamageMultiplier;
   }

   public boolean GetRoll() {
       return Rolled;
   }
   public Whf_SkavenGambleDamageComponent(){}
   public  Whf_SkavenGambleDamageComponent(Double chance) {
       this.Chance = chance;
   }
   public  Whf_SkavenGambleDamageComponent(Whf_SkavenGambleDamageComponent other) {this.Chance = other.Chance;}

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new Whf_SkavenGambleDamageComponent(this);
    }

    public static final BuilderCodec<Whf_SkavenGambleDamageComponent> CODEC = BuilderCodec.builder(
                    Whf_SkavenGambleDamageComponent.class, Whf_SkavenGambleDamageComponent::new)
            .append(new KeyedCodec<>("Chance", Codec.DOUBLE),
                    (c, v) -> c.Chance = v,
                    c -> c.Chance).add()
            .append(new KeyedCodec<>("Value", Codec.FLOAT),
                    (c, v) -> c.SelfDamage = v,
                    c -> c.SelfDamage).add()
            .build();
}
