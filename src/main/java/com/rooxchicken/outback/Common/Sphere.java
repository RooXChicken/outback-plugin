package com.rooxchicken.outback.Common;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Sphere
{
    private double[] cacheX;
    private double[] cacheZ;

    private double[] cacheYX;
    private double[] cacheYZ;

    public Color[] colors;

    public Sphere(Color[] _colors, int count)
    {
        cacheX = new double[count];
        cacheZ = new double[count];

        cacheYX = new double[count];
        cacheYZ = new double[count];

        for(int i = 0; i < count; i++)
        {
            double rad = Math.toRadians(i*(360.0/count));
            cacheX[i] = Math.sin(rad);
            cacheZ[i] = Math.cos(rad);

            cacheYX[i] = Math.sin(Math.toRadians((i * (90.0/count) ) * 2 - 90.0));
            cacheYZ[i] = Math.sin(Math.toRadians((i * (90.0/count) ) * 2));
        }

        colors = _colors;
    }

    public void run(Location pos, double size, int count, float particleSize, double offset)
    {
        for(int i = 0; i < count; i++)
        {
            double sphereOffset = cacheYX[i] * size;
            double sphereOffsetXZ = cacheYZ[i] * size;

            for(int k = 0; k < count; k++)
            {
                Location particlePos = pos.clone();
                double xOffset = cacheX[k];
                double zOffset = cacheZ[k];

                pos.getWorld().spawnParticle(Particle.DUST, particlePos.add(xOffset * sphereOffsetXZ, sphereOffset, zOffset * sphereOffsetXZ), 1, offset, offset, offset, new Particle.DustOptions(colors[(int)(Math.random()*colors.length)], particleSize));
            }
        }
    }
}
