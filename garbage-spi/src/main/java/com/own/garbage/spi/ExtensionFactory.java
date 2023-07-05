package com.own.garbage.spi;

/**
 * @ClassName ExtensionFactory
 * @Description 扩展工厂
 * @Author liugs
 * @Date 2023/7/5 0005 16:33
 */
@SPI("spi")
public interface ExtensionFactory {

    /**
     * Gets Extension.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param clazz the clazz
     * @return the extension
     */
    <T> T getExtension(String key, Class<T> clazz);
}
