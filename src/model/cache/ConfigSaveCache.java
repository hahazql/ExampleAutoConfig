package model.cache;

import model.bean.ConfigData;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zql on 2015/10/13.
 */
public class ConfigSaveCache
{
    private static ConcurrentHashMap<Integer,Boolean> _ix_update_cache = new ConcurrentHashMap<>();

    public static void put(Integer id)
    {
        _ix_update_cache.put(id,true);
    }
}
