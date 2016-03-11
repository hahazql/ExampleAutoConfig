package model.cache;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by zql on 2015/10/16.
 */
public class ConfigLoaderCache
{
    public static ConcurrentLinkedQueue<String> jarPath = new ConcurrentLinkedQueue<>();
}
