package com.wechat.service;

public interface WechatMenuService {
    /**
     * upgrade wehcat custom menu
     * @param menuStr
     */
    String upgradeMenu(String menuStr);

    /**
     * get custom menu
     * @return
     */
    String getMenu();

    /**
     * del custom menu
     * @return
     */
    String delMenu();
}
