package net.rk.railroadways.entity.blockentity.custom;

public interface SyncableNode{
    // forces a blockentity to sync its data with a 'master' controller
    boolean isSyncable = true;
    boolean lockable = true;
}
