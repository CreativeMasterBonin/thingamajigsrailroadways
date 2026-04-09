package net.rk.railroadways.entity.blockentity.custom;

public interface SyncableNode{
    /**
     * forces a blockentity to sync its data with a 'master' controller
     */
    boolean isSyncable = true;

    public boolean hasTicks();

    public boolean hasFlashInterval();

    public boolean canFlashLights();

    public void setController(CrossingComponentControllerBE controller);
}
